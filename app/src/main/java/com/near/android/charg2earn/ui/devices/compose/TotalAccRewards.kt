package com.near.android.charg2earn.ui.devices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.singleClick
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.Black_item_title
import com.near.android.charg2earn.ui.compose.theme.GreenCoinColor
import com.near.android.charg2earn.R


@Composable
fun TotalAccRewards(
    modifier: Modifier = Modifier,
    rewardCount: String,
    earningRate: String,
    onClickClaim: () -> Unit
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.mipmap.devices_claim12),
            contentDescription = "image",
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.Center
        )

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (Total, ECNCount, Earning, Claim, ImageCo2) = createRefs()

            Text(
                text = stringResource(id = R.string.device_total_acc_rewards),
                fontSize = TitleCoinSize,
                style = textStyleMedium,
                color = Black_item_title,
                modifier = Modifier
                    .padding(top = TitlePaddingTop, start = TitlePaddingStart)
                    .constrainAs(Total) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    })
            Row(
                modifier = Modifier
                    .padding(start = TitlePaddingStart, top = 5.dp)
                    .constrainAs(ECNCount) {
                        start.linkTo(Total.start)
                        top.linkTo(Total.bottom)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = rewardCount,
                    fontSize = EcnSize,
                    style = textStyleBold,
                    color = Black_item_title,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .sizeIn(maxWidth = ECNWidth),
                    maxLines = 1
                )
                Text(
                    text = "ECN",
                    fontSize = EcnSize,
                    style = textStyleBold,
                    color = Black_item_title,
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_device_qution),
                contentDescription = "co2",
                modifier = Modifier
                    .padding(
                        top = ECNPaddingTop,
                        start = TitlePaddingStart,
                        bottom = ECNPaddingBottom
                    )
                    .constrainAs(ImageCo2) {
                        start.linkTo(Total.start)
                        top.linkTo(ECNCount.bottom)
                    }
            )
            Text(
                text = "${stringResource(R.string.device_earning_rate)}${earningRate}ECN/S",
                fontSize = EarningSize,
                color = GreenCoinColor,
                style = textStyleMedium,
                modifier = Modifier
                    .padding(
                        top = ECNPaddingTop,
                        start = ECNPaddingStart,
                        bottom = ECNPaddingBottom
                    )
                    .constrainAs(Earning) {
                        start.linkTo(ImageCo2.end)
                        top.linkTo(ImageCo2.top)
                        bottom.linkTo(ImageCo2.bottom)
                    })
            Box(modifier = Modifier
                .padding(top = ClaimPaddingTop, end = ClaimPaddingEnd)
                .constrainAs(Claim) {
                    end.linkTo(parent.end)
                    top.linkTo(Total.top)
                }
                .singleClick { onClickClaim() }) {
                Image(
                    painter = painterResource(R.mipmap.devices_claim_btn3),
                    contentDescription = "Claim",
                )
            }
        }
    }
}

private val ECNPaddingTop = 30.dp
private val ECNPaddingBottom = 34.dp
private val ECNPaddingStart = 8.dp
private val TitlePaddingTop = 54.dp
private val ClaimPaddingTop = 52.dp
private val ClaimPaddingEnd = 30.dp
private val TitlePaddingStart = 42.dp
private val TitleCoinSize = 14.sp
private val EarningSize = 10.sp
private val EcnSize = 24.sp
private val ECNWidth = 110.dp

