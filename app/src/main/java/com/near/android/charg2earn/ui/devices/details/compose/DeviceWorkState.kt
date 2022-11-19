package com.near.android.charg2earn.ui.devices.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.GrayBackColor
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.ui.compose.theme.WhiteUntilColor
import com.near.android.charg2earn.ui.devices.details.viewmodel.DeviceState
import com.near.android.charg2earn.R


@Composable
fun DeviceChangeState(
    modifier: Modifier = Modifier,
) {
    ConstraintLayout(
        modifier = modifier
            .width(CardWhite)
            .height(CardHeight)
            .background(GrayBackColor, shape = RoundedCornerShape(12.dp))
    ) {
        val (title, textValue) = createRefs()
        Text(text = stringResource(R.string.device_status),
            color = Color.White,
            fontSize = TitleSize,
            style = textStyleMedium,
            modifier = Modifier
                .padding(top = TextPaddingStart, start = TextPaddingStart)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Text(text = "Input",
            color = GreenColor,
            fontSize = StateValueSize,
            style = textStyleBold,
            modifier = Modifier
                .padding(top = StateValuePaddingTop)
                .constrainAs(textValue) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })

    }
}

@Composable
fun DeviceWorkState(
    modifier: Modifier = Modifier,
    mDeviceState: DeviceState
) {
    ConstraintLayout(
        modifier = modifier
            .width(CardWhite)
            .height(CardHeight)
            .background(GrayBackColor, shape = MRoundedShape)
    ) {
        val (title, textValue, picture) = createRefs()
        Text(text = stringResource(mDeviceState.name),
            color = Color.White,
            fontSize = TitleSize,
            style = textStyleMedium,
            modifier = Modifier
                .padding(top = TextPaddingStart, start = TextPaddingStart)
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                })
        Text(text = buildAnnotatedString {
            append(mDeviceState.value)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                    fontSize = ValueUnitSize,
                    color = WhiteUntilColor
                )
            ) {
                append(mDeviceState.unit)
            }
        },
            color = Color.White,
            fontSize = ValueSize,
            style = textStyleBold,
            modifier = Modifier
                .padding(start = TextPaddingStart, top = ValuePaddingTop)
                .constrainAs(textValue) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                })

        Image(
            painter = painterResource(id = mDeviceState.img),
            contentDescription = "temperature",
            modifier = Modifier
                .padding(picturePadding)
                .constrainAs(picture) {
                    top.linkTo(title.bottom)
                    end.linkTo(parent.end)
                }
        )
    }
}

private val picturePadding = 10.dp
private val TextPaddingStart = 15.dp
private val CardHeight = 100.dp
private val CardWhite = 158.dp
private val TitleSize = 10.sp
private val StateValueSize = 24.sp
private val ValueUnitSize = 12.sp
private val ValueSize = 18.sp
private val ValuePaddingTop = 4.dp
private val StateValuePaddingTop = 20.dp
