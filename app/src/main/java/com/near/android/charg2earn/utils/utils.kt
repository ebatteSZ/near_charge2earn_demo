package com.near.android.charg2earn.utils

import com.google.gson.Gson
import com.google.gson.internal.`$Gson$Types`
import java.lang.reflect.Type
import java.util.*

inline fun <reified T> String.toMObject(): List<T> {
    val listType: Type =
        `$Gson$Types`.newParameterizedTypeWithOwner(null, ArrayList::class.java, T::class.java)
    return if (!contains("[")) {
        Gson().fromJson("[${this}]", listType)
    } else {
        Gson().fromJson(this, listType)
    }
}

fun bytesToHex(bytes: ByteArray): String? {
    val md5str = StringBuffer()
    var digital: Int
    for (i in bytes.indices) {
        digital = bytes[i].toInt()
        if (digital < 0) {
            digital += 256
        }
        if (digital < 16) {
            md5str.append("0")
        }
        md5str.append(Integer.toHexString(digital))
    }
    return md5str.toString().uppercase(Locale.getDefault())
}