package com.near.android.charg2earn.ui.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.model.MetadataModel
import com.near.android.charg2earn.model.TokensOwnerModel
import com.near.android.charg2earn.ui.compose.theme.*
import com.near.android.charg2earn.R



@Composable
fun DeviceItem(
    modifier: Modifier = Modifier,
    tokenOwner: TokensOwnerModel,
    stateName: String = "Normal",
    stateColor: Color = home_item_normal_color
) {
    ConstraintLayout(
        modifier = modifier
            .drawColoredShadow(ShadowColor, offsetY = 0.08.dp)
            .fillMaxWidth()
            .height(206.dp)
            .background(
                White,
                shape = MRoundedShape
            )
            .padding(bottom = ItemPaddingBottom)
    ) {
        val (image, title, subtitle, state) = createRefs()
        Image(painter = painterResource(id = R.mipmap.home_image_big),
            contentDescription = "image",
            modifier = Modifier
                .background(Color(0xffEEF1F3), MRoundedShape)
                .fillMaxWidth()
                .height(CardImageHeight)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        Text(text = tokenOwner.metadata.title,
            fontSize = CardNameSize,
            color = Black_item_title,
            style = textStyleBold,
            modifier = Modifier
                .padding(start = CardPaddingStart, top = CardNamePaddingTop)
                .constrainAs(title) {
                    top.linkTo(image.bottom)
                    start.linkTo(image.start)
                })
        Text(text = "x1.100 Booster",
            fontSize = CardSubtitleSize,
            color = Black_item_subtitle,
            style = textStyleMedium,
            modifier = Modifier
                .padding(start = CardPaddingStart, top = CardSubtitlePaddingTop)
                .constrainAs(subtitle) {
                    top.linkTo(title.bottom)
                    start.linkTo(title.start)
                })
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = CardPaddingStart, top = CardPaddingTop)
                .constrainAs(state) {
                    top.linkTo(subtitle.bottom)
                    start.linkTo(subtitle.start)
                }) {
            Box(
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(StateSize)
                    .background(stateColor, shape = RoundedCornerShape(60))
            )
            Text(
                text = stateName,
                color = stateColor,
                fontSize = NameStateSize,
                style = textStyleBold,
            )
        }

    }
}

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}

private val ItemPaddingBottom = 10.dp
private val CardImageHeight = 120.dp
private val CardNameSize = 16.sp
private val CardSubtitlePaddingTop = 4.dp
private val CardSubtitleSize = 12.sp
private val CardPaddingStart = 10.dp
private val CardNamePaddingTop = 8.dp
private val CardPaddingTop = 12.dp
private val StateSize = 10.dp
private val NameStateSize = 12.sp

