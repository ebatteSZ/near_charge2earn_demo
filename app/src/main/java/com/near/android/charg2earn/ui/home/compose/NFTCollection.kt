package com.near.android.charg2earn.ui.home.compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.*
import com.near.android.charg2earn.ui.compose.theme.Black_item_subtitle
import com.near.android.charg2earn.ui.compose.theme.Black_item_title
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.ui.compose.theme.RaffleColor
import com.near.android.charg2earn.R


@Composable
fun NFTCollection(
    modifier: Modifier = Modifier,
    onClickRaffle: () -> Unit,
    onClickMint: () -> Unit
) {
    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (name, priceTitle, price, bottom) = createRefs()
        Text(
            text = stringResource(R.string.nft_collection),
            fontSize = nameFontSize,
            style = textStyleBold,
            color = Black_item_title,
            modifier = Modifier.constrainAs(name) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        )
        Text(
            text = stringResource(R.string.nft_price),
            fontSize = NftPriceSize,
            style = textStyleMedium,
            color = Black_item_subtitle,
            modifier = Modifier.constrainAs(priceTitle) {
                end.linkTo(parent.end)
                top.linkTo(name.top)
            }
        )
        Text(
            text = "=＄0.001",
            fontSize = NftPriceSize,
            style = textStyleMedium,
            color = Black_item_subtitle,
            modifier = Modifier.constrainAs(price) {
                end.linkTo(parent.end)
                top.linkTo(priceTitle.bottom)
            }
        )
        Row(
            Modifier
                .padding(top = BtnVertical)
                .fillMaxWidth()
                .constrainAs(bottom) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            NFTBottom(
                onClick = { onClickRaffle() },
                modifier = Modifier
                    .height(BtnHeight)
                    .width(BtnWidth),
                colors = ButtonDefaults.buttonColors(GreenColor)
            ) {
                Text(
                    text = stringResource(R.string.title_home_raffle),
                    fontSize = BtnRaffleSize,
                    style = textStyleBold,
                    color = Black_item_title,
                )
            }
            Box(modifier = Modifier.width(DividerWidth))
            NFTBottom(
                onClick = { onClickMint() },
                modifier = Modifier
                    .height(BtnHeight)
                    .width(BtnWidth),
                colors = ButtonDefaults.buttonColors(RaffleColor)
            ) {
                Text(
                    text = stringResource(R.string.testing_mint),
                    fontSize = BtnRaffleSize,
                    style = textStyleBold,
                    color = Color.White,
                )
            }
        }
    }
}

@Composable
fun NFTBottom(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = MRoundedShape,
        elevation = ButtonDefaults.buttonElevation(1.dp),
        contentPadding = PaddingValues(0.dp),
        colors = colors
    ) {
        content()
    }
}


private val nameFontSize = 18.sp
private val NftPriceSize = 10.sp
private val BtnRaffleSize = 16.sp
private val BtnHeight = 58.dp
private val BtnWidth = 158.dp
private val BtnVertical = 18.dp
private val DividerWidth = 19.dp