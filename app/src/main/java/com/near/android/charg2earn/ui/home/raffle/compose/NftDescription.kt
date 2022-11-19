package com.near.android.charg2earn.ui.home.raffle.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.Black_item_subtitle
import com.near.android.charg2earn.R


@Preview
@Composable
fun NftDescription(modifier: Modifier=Modifier) {
    Box(
        modifier = Modifier
            .padding(top = NFTDescPaddingTop, start = PaddingHorizontal, end = PaddingHorizontal )
            .fillMaxWidth().then(modifier)
    ) {
        Text(
            text = stringResource(id = R.string.nft_raffle_desc),
            fontSize = NftDetailsFont,
            style = textStyleMedium,
            color = Black_item_subtitle,
        )
    }
}

private val NFTDescPaddingTop = 10.dp
private val PaddingHorizontal = 20.dp
private val NftDetailsFont = 14.sp
