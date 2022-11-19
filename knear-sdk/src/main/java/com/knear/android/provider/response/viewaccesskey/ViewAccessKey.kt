package com.knear.android.provider.response.viewaccesskey

import com.google.gson.annotations.SerializedName
import okhttp3.Response
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.knear.android.provider.response.errorresponse.ErrorResponse

open class ViewAccessKey(
    @SerializedName("jsonrpc") var jsonrpc: String? = null,
    @SerializedName("result") var result: Result = Result(),
    @SerializedName("id") var id: String? = null,
    @SerializedName("error") var error: ErrorResponse? = null
) {
    companion object {
        @JvmStatic
        @JvmName("create")
        fun Response.toViewAccessKey(): ViewAccessKey {
            val gson = Gson()
            if (this.isSuccessful) {
                try {
                    this.body?.let {
                        return gson.fromJson(it.string(), ViewAccessKey::class.java)
                    } ?: ViewAccessKey()
                } catch (exception: JsonSyntaxException) {
                    return ViewAccessKey(error = ErrorResponse(message = "permission"))
                }
            }
            return ViewAccessKey()
        }
    }
}
