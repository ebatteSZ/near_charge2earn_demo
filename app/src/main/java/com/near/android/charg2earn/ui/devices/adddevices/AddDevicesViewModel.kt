package com.near.android.charg2earn.ui.devices.adddevices

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.near.android.charg2earn.base.BaseViewModel
import com.near.android.charg2earn.ui.eventbus.EventMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import java.util.concurrent.ThreadLocalRandom

class AddDevicesViewModel(application: Application) : BaseViewModel(application) {

    private fun random(): String {
        val sb = StringBuilder()
        (0 until 3).forEach { _ ->
            val random = ThreadLocalRandom.current().nextInt(0, 9)
            sb.append(random)
        }
        return sb.toString()
    }

    private var _progress = MutableStateFlow(false)
    val isProgress: StateFlow<Boolean> get() = _progress.asStateFlow()


    private fun setDeviceId(): String {
        return "EN${System.currentTimeMillis()}${random()}"
    }

    fun pairing(activity: AddDevicesActivity) {
        viewModelScope.launch {
            _progress.emit(true)
            val signature = signature(setDeviceId(), "pairing")
            val args = "{\"device_id\": \"${setDeviceId()}\",\"signature\":\"$signature\"}"
            val methodName = "pairing"
            val response =
                nearMainService.callViewFunctionTransaction(contractName, methodName, args)
            _progress.emit(false)
            response.result.status.apply {
                if (Failure == null && SuccessValue != null) {
                    EventBus.getDefault().postSticky(EventMessage(1))
                    activity.finish()
                } else {
                    showToast()
                }
            }

        }
    }
}