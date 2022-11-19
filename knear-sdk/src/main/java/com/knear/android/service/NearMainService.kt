package com.knear.android.service

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import com.knear.android.NearService
import com.knear.android.scheme.KeyPair
import com.knear.android.scheme.KeyPairEd25519
import com.knear.android.scheme.Transaction
import com.syntifi.crypto.key.encdec.Base58
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NearMainService(context: Context) {
    private var networkId = "wallet.testnet.near.org"
    private var accountId: String = "testnet"
    private val walletUrl = "https://wallet.testnet.near.org/login/"
    private val walletUrl2 = "https://wallet.testnet.near.org/sign/"
    private val rcpEndpoint = "https://rpc.testnet.near.org"
    private val redirectUri = "near://success-auth"

    private var allKeys: String = ""
    private var publicKey: String = ""
    private val nearService: NearService =
        NearService(
            walletUrl,
            rcpEndpoint,
            context.getSharedPreferences(SPName, AppCompatActivity.MODE_PRIVATE)
        )
    private lateinit var privateKey: KeyPairEd25519
    val androidKeyStore: AndroidKeyStore =
        AndroidKeyStore(
            context.getSharedPreferences(
                SPName,
                AppCompatActivity.MODE_PRIVATE
            )
        )


    fun login(activity: Activity, email: String) {
        this.privateKey = KeyPair.fromRandom("ed25519")
        loginOAuth(activity)
    }

    private fun loginOAuth(activity: Activity) {
        val loggingUri = this.nearService.startLogin(this.privateKey.publicKey, redirectUri)
        this.androidKeyStore.setKey(networkId, accountId, this.privateKey)
        val intent = Intent(Intent.ACTION_VIEW, loggingUri)
        activity.startActivity(intent)
    }

    fun sign(activity: Activity) {
        val loggingUri = this.nearService.sign(accountId, accountId, "0.0025")
        val intent = Intent(Intent.ACTION_VIEW, loggingUri)
        activity.startActivity(intent)
    }

    fun attemptLogin(uri: Uri?): Boolean {
        var success = false
        uri?.let { it ->
            if (it.toString().startsWith(redirectUri)) {
                val currentAccountId = uri.getQueryParameter("account_id")
                val currentPublicKey = uri.getQueryParameter("public_key")
                val currentKeys = uri.getQueryParameter("all_keys")

                if (!currentAccountId.isNullOrEmpty() && !currentKeys.isNullOrEmpty() && !currentPublicKey.isNullOrEmpty()) {

                    accountId = currentAccountId
                    allKeys = currentKeys
                    publicKey = currentPublicKey

                    this.androidKeyStore.setAccountId(accountId)
                    this.androidKeyStore.setNetworkId(networkId)
                    this.androidKeyStore.setKey(networkId, accountId, privateKey)

                    androidKeyStore.getKey(networkId, accountId)?.let { key ->
                        this.privateKey = key
                        this.nearService.finishLogging(networkId, this.privateKey, accountId)
                        success = true
                    }

                }
            }
        }
        return success
    }

    fun signature(secretKey: String, publicKey: String, message: String): ByteArray {
        val keypair = KeyPairEd25519(secretKey, publicKey)
        return keypair.sign(message.toByteArray())
    }

    fun getEncodeSecret(secretKey: String): String = Base58.encode(secretKey.toByteArray())

    fun sendTransaction(receiver: String, amount: String) =
        this.nearService.sendMoney(accountId, receiver, amount)


    suspend fun callViewFunction(
        contractName: String,
        total: String,
        totalSupplyArgs: String
    ) = withContext(Dispatchers.IO) {
        nearService.callViewFunction(
            accountId,
            contractName,
            total,
            totalSupplyArgs
        )
    }

    suspend fun callViewFunctionTransaction(
        contractName: String,
        methodName: String,
        balanceOfArgs: String,
        gas: Long = 30000000000000,
        attachedDeposit: String = "0"
    ) = withContext(Dispatchers.IO) {
        nearService.callViewFunctionTransaction(
            accountId,
            contractName,
            methodName,
            balanceOfArgs,
            gas,
            attachedDeposit
        )
    }

}

const val SPName = "com.jose.lujan.near.android.sdk.NearMainService"

