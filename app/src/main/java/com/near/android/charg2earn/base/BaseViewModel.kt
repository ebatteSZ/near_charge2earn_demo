package com.near.android.charg2earn.base

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.knear.android.service.NearMainService
import com.near.android.charg2earn.AppApplication
import com.near.android.charg2earn.R
import com.near.android.charg2earn.utils.bytesToHex
import java.math.BigDecimal
import java.util.*

open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    val app = (application as AppApplication)
    val nearMainService: NearMainService = app.nearMainService
    val contractName = app.contractName
    val nftContractName = app.nftContractName
    val accountId = nearMainService.androidKeyStore.getAccountId().toString()

    val String.toBase64 get() = run { String(Base64.getDecoder().decode(this)) }

    val BigDecimal.toPow18: BigDecimal get() = run { divide(BigDecimal(10).pow(18)) }

    fun signature(deviceId: String, str: String): String? {
        val message = "$deviceId${System.currentTimeMillis()}$str"
        val encodedSecret = nearMainService.getEncodeSecret(app.privateKey)
        val encodedPublic = nearMainService.getEncodeSecret(app.publicKey)
        val key = nearMainService.signature(encodedSecret, encodedPublic, message)
        return bytesToHex(key)
    }

    fun byteToAscii(byteArray: ByteArray) = String(byteArray, Charsets.US_ASCII)

    fun showToast(
        activity: Context = app.applicationContext,
        @StringRes message: Int = R.string.network_request_error
    ) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }


}
