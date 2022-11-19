use std::collections::HashMap;
use near_contract_standards::fungible_token::metadata::{
    FungibleTokenMetadata, FungibleTokenMetadataProvider, FT_METADATA_SPEC,
};
use near_contract_standards::fungible_token::FungibleToken;
use near_sdk::borsh::{self, BorshDeserialize, BorshSerialize};
use near_sdk::collections::{LazyOption, UnorderedMap, LookupMap, UnorderedSet};
use near_sdk::json_types::U128;
use near_sdk::serde::{Deserialize, Serialize};
use near_sdk::{
    env, near_bindgen, AccountId, Balance, PanicOnDefault, Promise, PromiseOrValue,
    BorshStorageKey, log, assert_self, Gas, ext_contract, PromiseResult
};
use ed25519_dalek::{PublicKey, SignatureError, Keypair, Signature, SIGNATURE_LENGTH, PUBLIC_KEY_LENGTH};
use ed25519_dalek::Verifier;
use hex::FromHex;
use near_units::parse_gas;

pub type DeviceId = String;
pub const PUBLIC_KEY_STR: &[u8] = b"230b2ad496a8c99e5be375b454ea251a4aa4a1ab4e3e1d98e8a5d26c75d5e399";
pub const GAS_FOR_CALL: Gas = Gas(parse_gas!("25 Tgas") as u64);

#[ext_contract(ext_non_fungible_token)]
pub trait NonFungibleTokenContract {
    fn nft_supply_for_owner(&self, account_id: AccountId) -> U128;
}

#[ext_contract(ext_self)]
pub trait ExtSelf {
    fn nft_supply_callback(
        &mut self,
        account_id: AccountId,
        devices: Vec<DeviceId>
    ) -> Promise;
}

#[near_bindgen]
#[derive(BorshDeserialize, BorshSerialize, PanicOnDefault)]
pub struct Contract {
    pub token: FungibleToken,
    pub icon: Option<String>,
    pub device_by_owner: LookupMap<AccountId, UnorderedSet<DeviceId>>,
    pub device_owner: LookupMap<DeviceId, AccountId>,
    pub device_staked: LookupMap<DeviceId, u64>,
    pub histroy_reward: u128,
    pub reward_per_weight: u128,
    pub nft_contract: AccountId,
}

#[derive(BorshSerialize, BorshStorageKey)]
pub enum StorageKey {
    Token,
    DeviceByOwner,
    DeviceOwner,
    DeviceStaked,
    DeviceByOwnerPerOwner {
        account_id: String
    }
}

#[near_bindgen]
impl Contract {
    #[init]
    pub fn new(icon: Option<String>, total_supply: U128, reward_per_weight: u128, nft_contract: AccountId) -> Self {
        let mut this = Self {
            token: FungibleToken::new(StorageKey::Token),
            icon: icon,
            device_by_owner: LookupMap::new(StorageKey::DeviceByOwner),
            device_owner: LookupMap::new(StorageKey::DeviceOwner),
            device_staked: LookupMap::new(StorageKey::DeviceStaked),
            histroy_reward: 0,
            reward_per_weight: reward_per_weight,
            nft_contract: nft_contract
        };
        this.token.internal_register_account(&env::current_account_id());
        this.token.internal_deposit(&env::current_account_id(), total_supply.into());
        this
    }

    pub fn update_weight(&mut self, reward_per_weight: u128) {
        assert_self();
        self.reward_per_weight = reward_per_weight
    }

    pub fn devices(&self, account_id: AccountId) -> Vec<DeviceId> {
        let owner_devices = self.device_by_owner.get(&account_id);
        if owner_devices.is_some() {
            owner_devices.unwrap().to_vec()
        } else {
            Vec::new()
        }
    }

    pub fn device_reward(&self, device_id: DeviceId) -> u64 {
        self.device_staked.get(&device_id).unwrap_or(0)
    }

    pub fn nft_supply_callback(
        &mut self,
        account_id: AccountId,
        devices: Vec<DeviceId>
    ) -> PromiseOrValue<bool> {
        assert_eq!(
            env::predecessor_account_id(),
            env::current_account_id(),
            "Only contract callback is supported"
        );
        if let PromiseResult::Successful(value) = env::promise_result(0) {
            if let Ok(response) = near_sdk::serde_json::from_slice::<U128>(&value) {
                let mut total_interval = 0;
                let mut supply = u128::from(response);
                if supply == 0 {
                    supply = 1;
                } else {
                    supply = supply * 2;
                }
                for device_id in devices[0].split(',') {// devices.iter() {
                    let interval = self.device_staked.get(&device_id.to_string()).unwrap_or(0);
                    if interval == 0 {
                        continue
                    }
                    // env::log_str(&format!("device_id @{}, tl @{}, l @{}", device_id, total_interval, interval));
                    total_interval += interval;
                    self.device_staked.insert(&device_id.to_string(), &0);
                }
                if total_interval == 0 {
                    env::panic_str("Total reward zero");
                }
                let reward: u128 = supply * total_interval as u128 * self.reward_per_weight;
                env::log_str(&format!("nft supply: @{}, supply weight: @{}, total interval: @{}, reward weight: @{}, reward: @{}", u128::from(response), supply, total_interval, self.reward_per_weight, reward));
                let balance = self.token.internal_unwrap_balance_of(&env::current_account_id());
                if let Some(new_token_balance) = balance.checked_sub(reward) {
                    self.token.internal_transfer(&env::current_account_id(), &account_id, reward, Some("Charge reward".to_string()));
                    PromiseOrValue::Value(true)
                } else {
                    PromiseOrValue::Value(false)
                }
            } else {
                env::panic_str("Serialize error");
            }
        } else {
            env::panic_str("Promise failed");
        }
    }

    // ed25519
    pub fn verify_request(&self, content: String, signature: String) -> bool {
        let pub_bytes: Vec<u8> = FromHex::from_hex(PUBLIC_KEY_STR).unwrap();
        let sig_bytes: Vec<u8> = FromHex::from_hex(signature).unwrap();
        let PUBLIC_KEY: PublicKey = PublicKey::from_bytes(&pub_bytes[..PUBLIC_KEY_LENGTH]).unwrap();
        let sig: Signature = Signature::from_bytes(&sig_bytes[..]).unwrap();
        PUBLIC_KEY.verify(content.as_bytes(), &sig).is_ok()
    }

    pub fn pairing(&mut self, device_id: DeviceId, signature: Option<String>) -> bool{
        if self.device_owner.contains_key(&device_id) {
            log!("Device paired with user");
            return false;
        }
        self.device_owner.insert(&device_id, &env::signer_account_id());
        let mut device_ids = self.device_by_owner.get(&env::signer_account_id()).unwrap_or_else(|| {
            UnorderedSet::new(StorageKey::DeviceByOwnerPerOwner {
                account_id: env::signer_account_id().to_string(),
            })
        });
        device_ids.insert(&device_id);
        self.device_by_owner.insert(&env::signer_account_id(), &device_ids);
        log!("Add device to user");
        true
    }

    pub fn unpairing(&mut self, device_id: DeviceId, signature: Option<String>) -> bool{
        self.verify_owner(&device_id);
        self.device_owner.remove(&device_id);
        let mut owner_devices = self.device_by_owner.get(&env::signer_account_id()).unwrap_or_else(|| {
            env::panic_str("Unauthorized account and device")
        });
        owner_devices.remove(&device_id);
        if owner_devices.is_empty() {
            self.device_by_owner.remove(&env::signer_account_id());
        } else {
            self.device_by_owner.insert(&env::signer_account_id(), &owner_devices);
        }
        log!("Remove device from user");
        true
    }

    pub fn recycle(&mut self, device_id: DeviceId, signature: Option<String>) -> bool {
        self.verify_owner(&device_id);
        self.device_owner.insert(&device_id, &env::current_account_id());
        let mut owner_devices = self.device_by_owner.get(&env::signer_account_id()).unwrap_or_else(|| {
            env::panic_str("Unauthorized account and device")
        });
        owner_devices.remove(&device_id);
        if owner_devices.is_empty() {
            self.device_by_owner.remove(&env::signer_account_id());
        } else {
            self.device_by_owner.insert(&env::signer_account_id(), &owner_devices);
        }
        let reward: u128 = 3_000_000_000_000_000_000_000_000_000;
        log!("Recycle, reward @{}", reward);
        let balance = self.token.internal_unwrap_balance_of(&env::current_account_id());
        if let Some(new_token_balance) = balance.checked_sub(reward) {
            self.token.internal_transfer(&env::current_account_id(), &env::signer_account_id(), reward, Some("Recycle reward".to_string()));
            true
        } else {
            false
        }
    }

    fn verify_owner(&self, device_id: &DeviceId) {
        let device_owner = self.device_owner.get(device_id).unwrap_or_else(|| { env::panic_str("Device not paired") });
        if device_owner != env::signer_account_id() {
            env::panic_str("Unauthorized account");
        }
    }

    pub fn check_in(&mut self, device_id: DeviceId, start_time: u64, end_time: u64, signature: Option<String>) -> u64 {
        self.verify_owner(&device_id);
        let nano_to_sec = 1000000000 as u64;
        let now = env::block_timestamp() as u64  / nano_to_sec;
        if end_time <= start_time || end_time > now {
            env::panic_str("Unvalid param")
        }
        let diff = end_time - start_time;
        let mut interval = self.device_staked.get(&device_id).unwrap_or(0);
        interval += diff;
        self.device_staked.insert(&device_id, &interval);
        interval
    }

    pub fn claim_reward(&mut self, devices: Vec<DeviceId>) -> PromiseOrValue<bool> {
        ext_non_fungible_token::nft_supply_for_owner(
            env::signer_account_id(),
            self.nft_contract.clone(),
            0,                  
            GAS_FOR_CALL,       
        ).then(ext_self::nft_supply_callback(
            env::signer_account_id(),
            devices,
            env::current_account_id(),
            0,
            GAS_FOR_CALL * 2,
        )).into()
    }
}

near_contract_standards::impl_fungible_token_core!(Contract, token);
near_contract_standards::impl_fungible_token_storage!(Contract, token);

#[near_bindgen]
impl FungibleTokenMetadataProvider for Contract {
    fn ft_metadata(&self) -> FungibleTokenMetadata {
        FungibleTokenMetadata {
            spec: FT_METADATA_SPEC.to_string(),
            name: "Ebatte".to_string(),
            symbol: "ECN".to_string(),
            icon: self.icon.clone(),
            reference: None,
            reference_hash: None,
            decimals: 18
        }
    }
}