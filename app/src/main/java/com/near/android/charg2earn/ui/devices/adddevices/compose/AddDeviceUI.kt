package com.near.android.charg2earn.ui.devices.adddevices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.AddDeviceColor
import com.near.android.charg2earn.ui.compose.theme.Black2
import com.near.android.charg2earn.ui.compose.theme.GrayBackColor
import com.near.android.charg2earn.ui.devices.adddevices.AddDevicesActivity
import com.near.android.charg2earn.ui.devices.adddevices.AddDevicesViewModel
import com.near.android.charg2earn.ui.more.CardButton
import kotlinx.coroutines.delay
import com.near.android.charg2earn.R


@Composable
fun AddDeviceUI(
    activity: AddDevicesActivity,
    viewModel: AddDevicesViewModel = viewModel()
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("animations/data.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    var timeInSec by remember { mutableStateOf(2) }
    LaunchedEffect(key1 = timeInSec) {
        if (timeInSec > 0) {
            delay(900L)
            timeInSec -= 1
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .background(Black2)
            .fillMaxSize(),
    ) {
        val (animations, scanningFinish, button, noFound) = createRefs()
        if (timeInSec > 0) {
            ScanningDevice(modifier = Modifier.constrainAs(animations) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
                bottom.linkTo(button.top)
            }, composition, progress)
        } else if (timeInSec == -1) {
            Column(
                modifier = Modifier
                    .padding(top = NoFountImagePaddingTop)
                    .constrainAs(noFound) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.add_device_no_found),
                    contentDescription = "no found",
                    modifier = Modifier.size(NoFountImageSize)

                )
                Text(
                    text = stringResource(id = R.string.device_add_devices_no_found),
                    color = AddDeviceColor,
                    style = textStyleMedium,
                    fontSize = NoFountTextSize
                )
            }
        } else {
            DiscoverDevice(modifier = Modifier.constrainAs(scanningFinish) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }) {
                viewModel.pairing(activity)
            }
        }


        if (timeInSec > 0) {
            CardButton(
                modifier = Modifier
                    .padding(
                        bottom = BottomPaddingBottom,
                        start = BottomPaddingHorizontal,
                        end = BottomPaddingHorizontal
                    )
                    .height(BottomHeight)
                    .fillMaxWidth()
                    .constrainAs(button) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                colors = ButtonDefaults.buttonColors(GrayBackColor),
                onClick = {
                    timeInSec = -1
                }) {
                Text(
                    text = stringResource(id = R.string.device_add_devices_stop),
                    color = Color.White,
                    style = textStyleBold,
                    fontSize = BottomTextSize
                )
            }
        }
    }
}

private val BottomHeight = 58.dp
private val BottomPaddingBottom = 30.dp
private val BottomPaddingHorizontal = 20.dp
private val BottomTextSize = 18.sp
private val NoFountTextSize = 16.sp
private val NoFountImagePaddingTop = 200.dp
private val NoFountImageSize = 140.dp

