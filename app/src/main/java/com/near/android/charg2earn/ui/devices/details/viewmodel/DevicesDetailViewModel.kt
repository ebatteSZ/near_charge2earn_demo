package com.near.android.charg2earn.ui.devices.details.viewmodel

import android.app.Application
import androidx.annotation.IdRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.near.android.charg2earn.R
import com.near.android.charg2earn.base.BaseViewModel
import com.near.android.charg2earn.ui.devices.WEIGHT
import com.near.android.charg2earn.ui.eventbus.EventMessage
import com.near.android.charg2earn.ui.home.HomeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import java.math.BigDecimal
import java.util.concurrent.ThreadLocalRandom


class DevicesDetailViewModel(application: Application) : BaseViewModel(application) {

    private var _deviceRewardCount = MutableStateFlow("0")
    val deviceRewardCount: StateFlow<String> get() = _deviceRewardCount.asStateFlow()

    private var temperature by mutableStateOf("22")
    private var voltage by mutableStateOf("4.51")
    private var current by mutableStateOf("1.02")

    init {
        var random = ThreadLocalRandom.current().nextDouble(0.1, 60.0)
        voltage = String.format("%.2f", random)
        random = ThreadLocalRandom.current().nextDouble(0.1, 60.0)
        current = String.format("%.2f", random)
        val temRandom = ThreadLocalRandom.current().nextInt(10, 30)
        temperature = temRandom.toString()
    }

    var isRefreshing by mutableStateOf(false)

    private var openDialogSuccess by mutableStateOf(false)
    fun setDialogSuccess(value: Boolean) {
        openDialogSuccess = value
    }

    fun isDialogSuccess() = openDialogSuccess

    private var isShowDialog by mutableStateOf(false)
    fun setRecycleDialog(value: Boolean) {
        isShowDialog = value
    }

    fun getShowDialog() = isShowDialog

    private var _progress = MutableStateFlow(false)
    val isProgress: StateFlow<Boolean> get() = _progress.asStateFlow()


    val stateList: List<DeviceState> = listOf(
        DeviceState(
            R.string.device_temperature,
            temperature,
            R.drawable.ic_device_details_temperature,
            "°C"
        ),
        DeviceState(R.string.device_voltage, voltage, R.drawable.ic_device_details_voltage, "V"),
        DeviceState(R.string.device_current, current, R.drawable.ic_device_details_current, "A")
    )

    fun getRefreshing(deviceId: String, isShowProcess: Boolean) {
        viewModelScope.launch {
            if (isShowProcess)
                _progress.emit(true)
            getDeviceReward(deviceId)
        }
    }

    private suspend fun getDeviceReward(deviceId: String) {
        val methodName = "device_reward"
        val args = "{\"device_id\": \"$deviceId\"}"
        val response =
            nearMainService.callViewFunction(contractName, methodName, args)
        isRefreshing = false
        _progress.emit(false)
        response.apply {
            if (error == null && result.result != null) {
                HomeViewModel.nftCount.collect { count ->
                    val string = byteToAscii(result.result!!)
                    val nftCount = if (count == 0) 1 else count
                    val value =
                        BigDecimal(string)
                            .multiply(BigDecimal(nftCount))
                            .multiply(BigDecimal(WEIGHT))
                    _deviceRewardCount.emit(String.format("%.2f", value))
                }
            }
        }
    }

    fun claimReward(deviceId: String) {
        viewModelScope.launch {
            _progress.emit(true)
            val args = "{\"devices\": [\"${deviceId}\"]}"
            val methodName = "claim_reward"
            val gas = 300000000000000
            val response =
                nearMainService.callViewFunctionTransaction(contractName, methodName, args, gas)
            _progress.emit(false)
            response.result.status.apply {
                if (Failure == null && SuccessValue != null) {
                    showToast(message = R.string.device_claim_ok)
                    getDeviceReward(deviceId)
                    EventBus.getDefault().post(EventMessage(2))
                } else {
                    showToast()
                }
            }
        }
    }

    fun recycle(deviceId: String) {
        viewModelScope.launch {
            _progress.emit(true)
            val methodName = "recycle"
            val signature = signature(deviceId, "recycle")
            val args = "{\"device_id\": \"$deviceId\",\"signature\":\"$signature\"}"
            val response =
                nearMainService.callViewFunctionTransaction(contractName, methodName, args)
            response.result.status.apply {
                _progress.emit(false)
                if (Failure == null && SuccessValue != null) {
                    EventBus.getDefault().postSticky(EventMessage(1))
                    setDialogSuccess(true)
                } else {
                    showToast()
                }
            }
        }
    }
}

class DeviceState(@IdRes val name: Int, val value: String, @IdRes val img: Int, val unit: String)
