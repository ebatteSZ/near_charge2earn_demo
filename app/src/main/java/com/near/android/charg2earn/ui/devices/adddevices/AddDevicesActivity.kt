package com.near.android.charg2earn.ui.devices.adddevices

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.near.android.charg2earn.base.BaseActivity
import com.near.android.charg2earn.base.composeui.CustomMenuBar
import com.near.android.charg2earn.base.composeui.DialogBoxLoading
import com.near.android.charg2earn.base.composeui.NftTopAppBar
import com.near.android.charg2earn.ui.compose.theme.Black2
import com.near.android.charg2earn.ui.devices.adddevices.compose.AddDeviceUI
import com.near.android.charg2earn.R


class AddDevicesActivity : BaseActivity(
    darkTheme = false,
    navigationBarTheme = false,
    statusBarColor = Black2
) {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AddDevicesActivity::class.java))
        }
    }

    private val viewModel by viewModels<AddDevicesViewModel>()
    override fun initialData() {

    }

    @Composable
    override fun SetContentUI() {
        val isProgress by viewModel.isProgress.collectAsState()
        NftTopAppBar(
            title = stringResource(R.string.device_add_devices),
            titleColor = Color.White,
            toolBarColor = Black2,
            imageMenu = {
                CustomMenuBar(imageMenu = R.drawable.icon_left_arrow, tintColor = Color.White) {
                    this.finish()
                }
            }) {
            Box(modifier = Modifier.fillMaxSize()) {
                AddDeviceUI(this@AddDevicesActivity)
                if (isProgress) {
                    DialogBoxLoading()
                }
            }
        }
    }
}