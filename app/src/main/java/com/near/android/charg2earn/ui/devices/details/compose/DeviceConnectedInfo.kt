package com.near.android.charg2earn.ui.devices.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.R


@Preview(showBackground = true, backgroundColor = 0xff09090F)
@Composable
fun DeviceConnectedInfo(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        val (Picture, Name, Linked, Connected, ChangeTime, Health) = createRefs()
        Box(modifier = Modifier
            .height(PictureHeight)
            .width(PictureWidth)
            .constrainAs(Picture) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }) {

            Image(
                painter = painterResource(id = R.mipmap.lifecycler_big),
                contentDescription = "Picture",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Text(text = stringResource(id = R.string.device_name),
            fontSize = DeviceNameSize,
            style = textStyleBold,
            color = White,
            modifier = Modifier
                .constrainAs(Name) {
                    start.linkTo(Picture.end)
                    bottom.linkTo(Linked.top)
                })
        Image(
            painter = painterResource(id = R.drawable.ic_device_linked),
            contentDescription = "",
            modifier = Modifier
                .padding(top = LinkedImagePadding)
                .size(LinkedImageSize)
                .constrainAs(Linked) {
                    start.linkTo(Picture.end)
                    bottom.linkTo(ChangeTime.top)

                })
        Text(text = stringResource(id = R.string.device_connected),
            fontSize = ConnectedSize,
            style = textStyleBold,
            color = GreenColor,
            modifier = Modifier
                .padding(start = LinkedImagePadding, top = LinkedImagePadding)
                .constrainAs(Connected) {
                    start.linkTo(Linked.end)
                    top.linkTo(Linked.top)
                    bottom.linkTo(Linked.bottom)
                })


        val text = remember {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = FontFamily(Font(R.font.montserrat_semibold)),
                        fontSize = TimeHourSize
                    )
                ) {
                    append("1h")
                }
                append("32min")
            }
        }
        val healthyText = remember {
            buildAnnotatedString { append("Healthy") }
        }
        BatteryStatus(
            modifier = Modifier
                .padding(top = BatteryStatusHealthyPaddingTop)
                .constrainAs(ChangeTime) {
                    start.linkTo(Picture.end)
                    bottom.linkTo(Picture.bottom)
                },
            imageId = R.drawable.ic_device_details_time,
            changeTime = text,
            timeDesc = "Time left",
        )
        BatteryStatus(
            modifier = Modifier
                .padding(
                    start = BatteryStatusHealthyPaddingHorizontal,
                    top = BatteryStatusHealthyPaddingTop,
                    end = BatteryStatusHealthyPaddingHorizontal
                )
                .constrainAs(Health) {
                    start.linkTo(ChangeTime.end)
                    top.linkTo(ChangeTime.top)
                    bottom.linkTo(ChangeTime.bottom)
                },
            imageId = R.drawable.ic_device_details_healthy,
            changeTime = healthyText,
            timeDesc = "Battery Status",
            changeTimeTop = 9,
            timeDescTop = 5,
            timeImg = 1
        )
    }
}

private val BatteryStatusHealthyPaddingHorizontal = 20.dp
private val BatteryStatusHealthyPaddingTop = 19.dp
private val TimeHourSize = 18.sp
private val LinkedImageSize = 20.dp
private val LinkedImagePadding = 4.dp
private val ConnectedSize = 14.sp
private val DeviceNameSize = 24.sp
private val PictureHeight = 198.dp
private val PictureWidth = 112.dp
