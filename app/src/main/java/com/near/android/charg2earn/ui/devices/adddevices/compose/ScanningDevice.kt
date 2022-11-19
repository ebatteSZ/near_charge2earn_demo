package com.near.android.charg2earn.ui.devices.adddevices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.near.android.charg2earn.R


@Composable
fun ScanningDevice(
    modifier: Modifier = Modifier,
    composition: LottieComposition?,
    progress: Float
) {
    Box(
        modifier = Modifier
            .padding(bottom = AnimationBoxPaddingBottom)
            .size(AnimationBoxSize)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.mipmap.add_animation),
            modifier = Modifier.size(AnimationSize),
            contentDescription = ""
        )
        LottieAnimation(composition = composition, progress = progress)
        Image(
            painter = painterResource(id = R.mipmap.add_bluetooth),
            contentDescription = "bluetooth",
            modifier = Modifier
                .height(BluetoothHeight)
                .width(BluetoothWidth)
        )
    }
}

private val BluetoothHeight = 110.dp
private val BluetoothWidth = 67.dp
private val AnimationSize = 233.dp
private val AnimationBoxSize = 253.dp
private val AnimationBoxPaddingBottom = 30.dp