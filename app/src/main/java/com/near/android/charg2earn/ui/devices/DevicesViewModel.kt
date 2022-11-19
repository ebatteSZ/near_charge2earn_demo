package com.near.android.charg2earn.ui.devices

import android.app.Application
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.near.android.charg2earn.base.BaseViewModel
import com.near.android.charg2earn.ui.eventbus.EventMessage
import com.near.android.charg2earn.ui.home.HomeViewModel
import com.near.android.charg2earn.utils.toMObject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.internal.wait
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal
import java.util.*
import java.lang.StringBuilder


const val WEIGHT = 0.001

class DevicesViewModel(application: Application) : BaseViewModel(application) {

    private var _isRefreshing by mutableStateOf(false)
    fun isRefreshing() {
        _isRefreshing = !_isRefreshing
        if (_isRefreshing) {
            refreshDevices(false)
        }
    }

    fun refreshing() = _isRefreshing


    private var _deviceRewardCount = MutableStateFlow("0")
    val deviceRewardCount: StateFlow<String> get() = _deviceRewardCount.asStateFlow()

    val devicesList = mutableStateListOf<String>()

    private var _progress = MutableStateFlow(false)
    val isProgress: StateFlow<Boolean> get() = _progress.asStateFlow()


    private var _earningRate = MutableStateFlow("0.16700x1.4137x2=0.472")
    val earningRateCount: StateFlow<String> get() = _earningRate.asStateFlow()


    fun refreshDevices(isShowProcess: Boolean) {
        viewModelScope.launch {
            if (isShowProcess)
                _progress.emit(true)
            getDevices(nearMainService.androidKeyStore.getAccountId() ?: "ebatte-test.testnet")
        }
    }

    fun setClaimReward() {
        viewModelScope.launch {
            _progress.emit(true)
            claimReward()
        }
    }

    private suspend fun getDevices(accountId: String) {
        val params = "{\"account_id\": \"$accountId\"}"
        val methodName = "devices"
        val response =
            nearMainService.callViewFunction(contractName, methodName, params)
        _isRefreshing = false
        _progress.emit(false)
        response.apply {
            if (error == null && result.result != null) {
                val string = byteToAscii(result.result!!)
                val list = string.toMObject<String>()
                devicesList.clear()
                devicesList.addAll(list)
                getDeviceReward()
                delay(1000)
                checkIn()
            } else {
                showToast()
            }
        }
    }

    private suspend fun checkIn() {
        if (devicesList.size < 1) {
            return
        }
        val methodName = "check_in"
        while (true) {
            val startTime = System.currentTimeMillis() / 1000
            delay(3000)
            try {
                for (i in 0 until devicesList.size) {
                    val endTime = System.currentTimeMillis() / 1000
                    val signature = signature(devicesList[i], "check_in")
                    val params =
                        "{\"device_id\": \"${devicesList[i]}\", \"start_time\": ${startTime}, \"end_time\": ${endTime}, \"signature\": \"$signature\"}"
                    nearMainService.callViewFunctionTransaction(
                        contractName,
                        methodName,
                        params
                    )
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun devicesIdToString(): String {
        val sb = StringBuilder()
        for (i in 0 until devicesList.size) {
            sb.append(devicesList[i])
            sb.append(",")
        }
        return sb.toString().substring(0, sb.length - 1)
    }

    private suspend fun claimReward() {
        if (devicesList.size < 1) {
            return
        }
        val args = "{\"devices\": [\"${devicesIdToString()}\"]}"
        val methodName = "claim_reward"
        val gas = 300000000000000
        val response =
            nearMainService.callViewFunctionTransaction(contractName, methodName, args, gas)
        response.result.status.apply {
            _progress.emit(false)
            if (Failure == null && SuccessValue != null) {
                showToast(message = com.near.android.charg2earn.R.string.device_claim_ok)
                getDeviceReward()
                EventBus.getDefault().post(EventMessage(2))
            } else {
                showToast()
            }
        }
    }

    private fun getDeviceReward() {
        if (devicesList.size < 1) return
        viewModelScope.launch {
            try {
                val sbTotal = StringBuilder("0")
                devicesList.forEach { deviceId ->
                    val methodName = "device_reward"
                    val args = "{\"device_id\": \"$deviceId\"}"
                    val response =
                        nearMainService.callViewFunction(
                            contractName,
                            methodName,
                            args
                        )
                    response.apply {
                        if (error == null && result.result != null) {
                            val string = byteToAscii(result.result!!)
                            val bigDecimal =
                                sbTotal.toString().toBigDecimal().add(BigDecimal(string))
                            sbTotal.clear()
                            sbTotal.append(bigDecimal)
                        } else {
                            showToast()
                        }
                    }
                }

                HomeViewModel.nftCount.collect { count ->
                    val nftCount = if (count == 0) 1 else count
                    val deviceSize = devicesList.size
                    val earningRate = BigDecimal(nftCount).multiply(BigDecimal(0.001))
                        .multiply(BigDecimal(deviceSize))
                    val rate = "$nftCount x 0.001 x $deviceSize = ${
                        String.format("%.5f", earningRate)
                    }"
                    _earningRate.value = ""
                    _earningRate.emit(rate)
                    val total = sbTotal.toString().toBigDecimal()
                        .multiply(BigDecimal(if (count == 0) 1 else count))
                        .multiply(BigDecimal(WEIGHT))
                    _deviceRewardCount.value = ""
                    _deviceRewardCount.emit(String.format("%.2f", total))
                }
            } catch (e: Exception) {
            }
        }
    }


    fun unpairing(deviceId: String) {
        viewModelScope.launch {
            _progress.emit(true)
            val methodName = "unpairing"
            val signature = signature(deviceId, "unpairing")
            val args = "{\"device_id\": \"$deviceId\",\"signature\":\"$signature\"}"
            val response =
                nearMainService.callViewFunctionTransaction(contractName, methodName, args)
            response.result.status.apply {
                _progress.emit(false)
                if (Failure == null && SuccessValue != null) {
                    refreshDevices(true)
                } else {
                    showToast()
                }
            }
        }
    }
}
