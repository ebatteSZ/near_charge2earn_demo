package com.near.android.charg2earn.ui.home.raffle.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.Black_item_subtitle
import com.near.android.charg2earn.ui.compose.theme.Black_item_title
import com.near.android.charg2earn.ui.home.raffle.RaffleDetailsViewModel
import  androidx.lifecycle.viewmodel.compose.viewModel
import com.near.android.charg2earn.R


@Composable
fun RaffleView(viewModel:RaffleDetailsViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        NFTImage(modifier = Modifier.align(Alignment.CenterHorizontally))
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = NamePaddingTop)) {
            Text(
                text = "Current Raffle",
                fontSize = CurrentRaffleFont,
                style = textStyleMedium,
                color = Black_item_subtitle,
                modifier = Modifier
                    .align(Alignment.CenterStart)
            )
            Text(
                text = "233 / 1000Tickets Left",
                fontSize = TicketsFont,
                style = textStyleMedium,
                color = Black_item_subtitle,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            )
        }
        Text(
            text = stringResource(id = R.string.nft_name),
            fontSize = NftNameFont,
            style = textStyleBold,
            color = Black_item_title,
            modifier = Modifier
                .padding(top = NamePaddingTop, start = NamePaddingTop)
        )
        NftDescription()
        Text(
            buildAnnotatedString {
                append("You have")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.SemiBold, fontFamily = FontFamily(
                            Font(R.font.montserrat_semibold)
                        ), fontSize = NftNameFont
                    )
                ) {
                    append(" 17 ")
                }
                append("tickets in this raffle\n")
                append("1ECN / Ticket")
            },
            fontSize = NftDetailsFont,
            style = textStyleMedium,
            color = Black_item_title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = TicketPaddingTop, bottom = TicketPaddingBottom),
            textAlign = TextAlign.Center
        )
        BuyTicketBtn(
            text = R.string.device_nft_buy_ticket
        ) {
            viewModel.ftTransfer()
        }
    }
}


private val CurrentRaffleFont = 16.sp
private val TicketsFont = 12.sp
private val NftNameFont = 24.sp
private val NftDetailsFont = 18.sp
private val NamePaddingTop = 20.dp
private val TicketPaddingTop = 40.dp
private val TicketPaddingBottom = 5.dp
