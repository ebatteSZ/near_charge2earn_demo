package com.near.android.charg2earn.ui.home.raffle.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.near.android.charg2earn.ui.compose.theme.MoreDividerColor
import com.near.android.charg2earn.R


@Preview
@Composable
fun NFTImage(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .padding( PaddingHorizontal)
            .size(ImageSize)
            .background(
                MoreDividerColor,
                shape = RoundedCornerShape(ImageRoundedShape)
            )
            .then(modifier)
    ) {
        Image(
            painter = painterResource(R.mipmap.home_image_big),
            contentDescription = "image",
            modifier = Modifier
                .fillMaxSize()
                .padding( start = PaddingHorizontal, end = PaddingHorizontal, top =PaddingTop )
        )
    }
}

private val ImageSize = 335.dp
private val ImageRoundedShape = 20.dp
private val PaddingTop = 38.dp
private val PaddingHorizontal = 20.dp