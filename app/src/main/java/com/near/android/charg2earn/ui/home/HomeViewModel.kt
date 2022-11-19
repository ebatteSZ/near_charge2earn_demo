package com.near.android.charg2earn.ui.home

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.knear.android.service.MethodUtils.Companion.getDecodedAsciiValue
import com.near.android.charg2earn.base.BaseViewModel
import com.near.android.charg2earn.model.TokensOwnerModel
import com.near.android.charg2earn.ui.compose.theme.home_item_epic_color
import com.near.android.charg2earn.ui.compose.theme.home_item_normal_color
import com.near.android.charg2earn.ui.compose.theme.home_item_rare_color
import com.near.android.charg2earn.utils.toMObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class HomeViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        private var _nftCount = MutableStateFlow(1)
        val nftCount: StateFlow<Int> = _nftCount.asStateFlow()
    }

    var isRefreshing by mutableStateOf(false)

    val itemList: List<Pair<String, Color>> = listOf(
        Pair("Normal", home_item_normal_color),
        Pair("Rare", home_item_rare_color),
        Pair("Epic", home_item_epic_color)
    )
    private var _progress = MutableStateFlow(false)
    val isProgress: StateFlow<Boolean> get() = _progress.asStateFlow()

    private val _tokensOwner = MutableStateFlow<List<TokensOwnerModel>>(emptyList())
    val tokensOwner: StateFlow<List<TokensOwnerModel>>
        get() = _tokensOwner.asStateFlow()

    private val _ftBalanceCount = MutableStateFlow(BigDecimal("0"))
    val ftBalanceCount: StateFlow<BigDecimal>
        get() = _ftBalanceCount.asStateFlow()

    private val _coinPrice = MutableStateFlow("0.00")
    val coinPrice: StateFlow<String>
        get() = _coinPrice.asStateFlow()

    fun networkRequest(isShowProcess: Boolean) {
        viewModelScope.launch {
            if (isShowProcess) {
                _progress.emit(true)
            }
            storageBalanceOf()
            networkRequest()
        }
    }

    suspend fun ftBalanceOf() {
        val methodName = "ft_balance_of"
        val balanceOfArgs =
            "{\"account_id\": \"${nearMainService.androidKeyStore.getAccountId()}\"}"
        val response = nearMainService.callViewFunction(
            contractName,
            methodName,
            balanceOfArgs
        )
        response.apply {
            if (error == null && result.result != null) {
                var string = byteToAscii(result.result!!)
                if (string.contains("\"")){
                    string=  string.replace("\"","")
                }
                _ftBalanceCount.emit(BigDecimal(string).toPow18)
                val bg = _ftBalanceCount.value
                _coinPrice.emit(String.format("%.2f", bg))
            }
        }
    }


    private suspend fun nftTokensForOwner() {
        val balanceOfArgs =
            "{\"account_id\": \"${nearMainService.androidKeyStore.getAccountId()}\"}"
        val methodName = "nft_tokens_for_owner"
        val response = nearMainService.callViewFunction(
            nftContractName,
            methodName,
            balanceOfArgs
        )
        isRefreshing = false
        _progress.emit(false)
        response.apply {
            if (error == null && result.result != null) {
                byteToAscii(result.result!!).toMObject<TokensOwnerModel>()
                    .let { list ->
                        _tokensOwner.emit(list)
                        _nftCount.emit(list.size)
                    }
            } else {
                showToast()
            }
        }
    }

    fun sendNftMint() {
        viewModelScope.launch {
            _progress.emit(true)
            val balanceOfArgs =
                "{ \"receiver_id\": \"${nearMainService.androidKeyStore.getAccountId()}\", \"title\": \"Ebatte OG\", \"media\": \"bafybeia2elvpfa6t7fpeej3wfh4p523fejiysy5adibtf5ad7twt65gr7y\", \"reference\": \"bafybeibemnamlqliohkxgize7sb7tlhgrnhhp4xui3thnbtg4j24ii3b5u/1.json\" }"
            val gas = 300000000000000
            val depositYocto = "1.01523"
            val methodName = "nft_mint"
            val balanceOfResponse =
                nearMainService.callViewFunctionTransaction(
                    nftContractName,
                    methodName,
                    balanceOfArgs,
                    gas,
                    depositYocto
                )
            _progress.emit(false)
            balanceOfResponse.result.status.apply {
                if (Failure == null && SuccessValue != null) {
                    networkRequest()
                } else {
                    showToast()
                }
            }
        }
    }

    private suspend fun storageBalanceOf() {
        val balanceOfArgs =
            "{\"account_id\": \"${nearMainService.androidKeyStore.getAccountId()}\"}"
        val methodName = "storage_balance_of"
        val response = nearMainService.callViewFunction(
            contractName,
            methodName,
            balanceOfArgs
        )
        response.apply {
            if (error == null && result.result != null) {
                val string = String(result.result!!, Charsets.US_ASCII)
                if (string=="null"){
                    storageDeposit()
                }
                Log.e("NearService", "storage_balance_of =$string")
            }
        }
    }

    private suspend fun storageDeposit() {
        val args = "{\"account_id\": \"${accountId}\", \"registration_only\": true}"
        val deposit = "0.25"
        val methodName = "storage_deposit"
        nearMainService.callViewFunctionTransaction(
            contractName = contractName,
            methodName = methodName,
            balanceOfArgs = args,
            attachedDeposit = deposit
        )
    }

    private suspend fun networkRequest() {
        ftBalanceOf()
        delay(200)
        nftTokensForOwner()
    }
}
