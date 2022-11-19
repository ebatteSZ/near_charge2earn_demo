package com.near.android.charg2earn.ui.home.raffle.compose

import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.singleClick
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.MoreTextColor
import com.near.android.charg2earn.R


@Composable
fun BuyTicketBtn(modifier: Modifier = Modifier,@IdRes text:Int, onClick:()->Unit) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (background, txt) = createRefs()
        Image(painter = painterResource(R.mipmap.buy_a_ticket),
            contentDescription = "buy",
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(background) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                })
        Box(modifier = Modifier
            .padding(
                start = BtnPaddingHor,
                top = BtnPaddingTop,
                end = BtnPaddingHor
            )
            .singleClick { onClick()}
            .fillMaxWidth()
            .height(BtnRecycleHeight)
            .background(Color.Transparent)
            .constrainAs(txt) {
                start.linkTo(background.start)
                end.linkTo(background.end)
            },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(text),
                fontSize = RecycleSize,
                color = MoreTextColor,
                style = textStyleBold,
                textAlign = TextAlign.Center
            )
        }

    }
}
private val BtnPaddingHor = 20.dp
private val BtnPaddingTop = 8.dp
private val RecycleSize = 18.sp
private val BtnRecycleHeight = 58.dp
