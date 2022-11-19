package com.near.android.charg2earn.ui.home.raffle

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.near.android.charg2earn.R
import com.near.android.charg2earn.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RaffleDetailsViewModel(application: Application) : BaseViewModel(application) {
    private var _progress = MutableStateFlow(false)
    val isProgress: StateFlow<Boolean> get() = _progress.asStateFlow()

    fun ftTransfer() {
        viewModelScope.launch {
            _progress.emit(true)
            val methodName = "ft_transfer"
            val balanceOfArgs =
                "{\"receiver_id\": \"android88.testnet\", \"amount\": \"3000000000000000000\"}"
            val amount = "0.000000000000000000000001"
            val response = nearMainService.callViewFunctionTransaction(
                nftContractName,
                methodName,
                balanceOfArgs,
                attachedDeposit = amount
            )
            _progress.emit(false)

            if (response.result.status.Failure == null) {
                showToast(message = R.string.transfer_success)
            } else {
                showToast()
            }
        }
    }
}