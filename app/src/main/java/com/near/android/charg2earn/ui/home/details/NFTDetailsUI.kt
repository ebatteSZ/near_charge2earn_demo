package com.near.android.charg2earn.ui.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.Black_item_subtitle
import com.near.android.charg2earn.ui.compose.theme.Black_item_title
import com.near.android.charg2earn.ui.home.raffle.compose.NFTImage
import com.near.android.charg2earn.ui.home.raffle.compose.NftDescription


@Preview
@Composable
fun NFTDetailsUI(nftName: String = "NFT-Name") {
    ConstraintLayout(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        val (CardImage, CurrentRaffle, NftName, NftDetails) = createRefs()

        NFTImage(modifier = Modifier.constrainAs(CardImage) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Text(
            text = "NFT-series",
            fontSize = CurrentRaffleFont,
            style = textStyleMedium,
            color = Black_item_subtitle,
            modifier = Modifier
                .padding(start = NamePaddingHorizontal)
                .constrainAs(CurrentRaffle) {
                    top.linkTo(CardImage.bottom)
                    start.linkTo(CardImage.start)

                })
        Text(
            text = nftName,
            fontSize = NftNameFont,
            style = textStyleBold,
            color = Black_item_title,
            modifier = Modifier
                .padding(top = NamePaddingTop, start = NamePaddingHorizontal)
                .constrainAs(NftName) {
                    top.linkTo(CurrentRaffle.bottom)
                    start.linkTo(CurrentRaffle.start)
                })
        NftDescription(modifier = Modifier.constrainAs(NftDetails){
            top.linkTo(NftName.bottom)
            start.linkTo(CardImage.start)
            end.linkTo(CardImage.end)
        })
    }
}


private val CurrentRaffleFont = 16.sp
private val NftNameFont = 24.sp
private val NamePaddingTop = 6.dp
private val NamePaddingHorizontal = 20.dp
