package com.near.android.charg2earn.ui.splash

import android.app.Activity
import android.app.Application
import android.net.Uri
import com.near.android.charg2earn.base.BaseViewModel

class SplashViewModel(application: Application) : BaseViewModel(application) {

    fun login(activity: Activity, email: String) {
        nearMainService.login(activity, email)
    }

    fun attemptLogin(uri: Uri?): Boolean {
        return nearMainService.attemptLogin(uri)
    }
}