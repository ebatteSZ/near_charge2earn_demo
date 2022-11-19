package com.near.android.charg2earn.ui

import android.app.Activity
import android.app.Application
import com.near.android.charg2earn.base.BaseViewModel


class MainViewModel(application: Application) : BaseViewModel(application) {
    fun sign(activity: Activity) {
        nearMainService.sign(activity)
    }


}