package com.near.android.charg2earn.ui.devices.details.compose

import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.AddDeviceColor
import com.near.android.charg2earn.ui.compose.theme.GrayBackColor


@Composable
fun BatteryStatus(
    modifier: Modifier = Modifier,
    @IdRes imageId: Int,
    changeTime: AnnotatedString,
    timeDesc: String,
    changeTimeTop: Int = 3,
    timeDescTop: Int = 3,
    timeImg: Int = 1
) {
    Column(
        modifier = Modifier
            .then(modifier)
            .width(CardWidth)
            .height(CardHeight)
            .background(GrayBackColor, shape = MRoundedShape),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(imageId),
            contentDescription = timeDesc,
            modifier = Modifier
                .padding(top = timeImg.dp)
                .size(CardImageSize)
        )
        Text(
            text = changeTime,
            color = Color.White,
            fontSize = ChangeTimeSize,
            style = textStyleBold,
            modifier = Modifier
                .padding(top = changeTimeTop.dp)
        )
        Text(
            text = timeDesc,
            color = AddDeviceColor,
            fontSize = TimeDescSize,
            style = textStyleMedium,
            modifier = Modifier
                .padding(top = timeDescTop.dp)
        )
    }
}

private val CardHeight = 112.dp
private val CardWidth = 97.dp
private val CardImageSize = 44.dp
private val ChangeTimeSize = 12.sp
private val TimeDescSize = 10.sp