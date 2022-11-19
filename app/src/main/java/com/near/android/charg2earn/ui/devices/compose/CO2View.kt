package com.near.android.charg2earn.ui.devices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.Black2
import com.near.android.charg2earn.ui.compose.theme.CO2BackColor
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.R


@Composable
fun CO2View(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .padding(bottom =CO2MarginBottom, start = CO2MarginHorizontal, end = CO2MarginHorizontal)
            .fillMaxWidth()
            .background(CO2BackColor, shape = RoundedCornerShape(CO2Shape))
            .padding(1.dp)
            .then(modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(CO2Height)
                .background(Black2, shape = RoundedCornerShape(CO2Shape))
                .padding(horizontal = CO2PaddingHorizontal),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_device_co2),
                contentDescription = "co2",
                modifier = Modifier.size(CO2ImageSize)
            )
            Text(
                text = "Cumulative Carbon Reduction: 2.013g CO2",
                fontSize = CO2TextSize,
                color = GreenColor,
                style = textStyleMedium,
                modifier = Modifier.padding(start = CO2TextMarginStart),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

private val CO2Height = 30.dp
private val CO2Shape = 20.dp
private val CO2TextSize = 12.sp
private val CO2ImageSize = 24.dp
private val CO2PaddingHorizontal = 15.dp
private val CO2MarginHorizontal = 20.dp
private val CO2MarginBottom = 90.dp
private val CO2TextMarginStart = 10.dp