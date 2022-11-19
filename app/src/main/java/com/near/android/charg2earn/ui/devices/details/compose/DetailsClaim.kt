package com.near.android.charg2earn.ui.devices.details.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.near.android.charg2earn.R


@Preview(showBackground = true)
@Composable
fun Show() {
    DetailsClaim(onClickClaim = {

    })
}

@Composable
fun DetailsClaim(rewardsCount: String = "1428, 57", onClickClaim: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.mipmap.devices_details_claim4),
            contentDescription = "image",
            modifier = Modifier.fillMaxWidth()
        )
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (Total, ECNCount, Claim) = createRefs()

            Text(
                text = stringResource(id = R.string.device_total_acc_rewards),
                fontSize = TitleCoinSize,
                style = textStyleMedium,
                color = Color.White,
                modifier = Modifier
                    .padding(top = TitlePaddingTop, start = TitlePaddingStart)
                    .constrainAs(Total) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    })
            Row(
                modifier = Modifier
                    .padding(start = TitlePaddingStart, top = EcnPaddingTop)
                    .constrainAs(ECNCount) {
                        start.linkTo(Total.start)
                        top.linkTo(Total.bottom)
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = rewardsCount,
                    fontSize = EcnSize,
                    style = textStyleBold,
                    color = Color.White,
                    modifier = Modifier
                        .sizeIn(maxWidth = ECNWidth),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    text = "ECN",
                    fontSize = EcnSize,
                    style = textStyleBold,
                    color = Color.White,
                )

            }

            Box(
                modifier = Modifier
                    .padding(top = ClaimPaddingTop, end = ImageCoinPadding)
                    .constrainAs(Claim) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .singleClick { onClickClaim() },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.mipmap.devices_details_claim_btn4),
                    contentDescription = "Claim",
                )
            }
        }
    }
}

private val ImageCoinPadding = 20.dp
private val TitlePaddingTop = 65.dp
private val ClaimPaddingTop = 62.dp
private val TitlePaddingStart = 40.dp
private val EcnPaddingTop = 10.dp
private val TitleCoinSize = 14.sp
private val EcnSize = 24.sp
private val ECNWidth = 110.dp

