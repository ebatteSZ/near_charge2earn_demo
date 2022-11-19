package com.near.android.charg2earn.ui.devices.adddevices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.GrayBackColor
import com.near.android.charg2earn.R


@Composable
fun AddDevicesItem(onClick: () -> Unit) {
    Row(
        Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(GrayBackColor, shape = MRoundedShape)
            .padding(horizontal = ItemPaddingHorizontal, vertical = ItemPaddingVertical),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.mipmap.lifcycle_4),
            contentDescription = "Picture",
            modifier = Modifier
                .width(PictureWidth)
                .height(PictureHeight)
        )
        Text(
            text = stringResource(id = R.string.device_name),
            fontSize = NameTextSize,
            color = Color.White,
            style = textStyleBold,
            modifier = Modifier.padding(start = NameTextPaddingStart)
        )
    }
}

private val PictureWidth = 40.dp
private val PictureHeight = 82.dp
private val NameTextSize = 18.sp
private val NameTextPaddingStart = 30.dp
private val ItemPaddingHorizontal = 20.dp
private val ItemPaddingVertical = 6.dp
