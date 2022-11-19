package com.near.android.charg2earn.ui.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.Black_item_title
import com.near.android.charg2earn.ui.compose.theme.GreenCoinColor
import com.near.android.charg2earn.ui.home.HomeViewModel
import com.near.android.charg2earn.R


@Composable
fun EbatteCoin(viewModel: HomeViewModel = viewModel()) {
    val balance by viewModel.ftBalanceCount.collectAsState()
    val coinPrice by viewModel.coinPrice.collectAsState()
    Box {
        Image(
            painter = painterResource(id = R.mipmap.home_ebatte_coin),
            contentDescription = "image",
            modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = EbatteCoinPaddingTop)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_home_ebatte_coin),
                    contentDescription = "ebatte coin",
                    modifier = Modifier
                        .padding(start = ImageCoinPadding)
                        .size(ImageCoinSize)
                )
                Column {
                    Text(
                        text = "ebatte Coin",
                        fontSize = TitleCoinSize,
                        style = textStyleBold,
                        color = Black_item_title,
                        modifier = Modifier
                            .padding(start = TitlePadding)
                    )
                    Text(
                        text = "＄0.001",
                        fontSize = CoinSize,
                        style = textStyleMedium,
                        color = GreenCoinColor,
                        modifier = Modifier
                            .padding(start = TitlePadding)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(
                        top = ECNPaddingTop,
                        start = ImageCoinPadding,
                        bottom = ECNPaddingBottom,
                        end = ImageCoinPadding
                    )
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$balance",
                        fontSize = EcnSize,
                        style = textStyleBold,
                        color = Black_item_title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.sizeIn(maxWidth = ECNWidth)
                    )
                    Text(
                        text = "ECN",
                        fontSize = EcnSize,
                        style = textStyleBold,
                        color = Black_item_title,
                    )
                }

                Text(
                    text = "  =  ＄${coinPrice}",
                    fontSize = MoneySize,
                    style = textStyleMedium,
                    color = Black_item_title,
                    modifier = Modifier
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }

    }
}

private val ImageCoinSize = 40.dp
private val ECNPaddingTop = 30.dp
private val EbatteCoinPaddingTop = 40.dp
private val ECNPaddingBottom = 33.dp
private val ImageCoinPadding = 40.dp
private val TitlePadding = 8.dp
private val ECNWidth = 110.dp
private val TitleCoinSize = 12.sp
private val CoinSize = 10.sp
private val EcnSize = 24.sp
private val MoneySize = 18.sp