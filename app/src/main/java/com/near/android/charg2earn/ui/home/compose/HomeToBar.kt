package com.near.android.charg2earn.ui.home.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.*
import com.near.android.charg2earn.ui.compose.theme.Black_item_subtitle
import com.near.android.charg2earn.ui.compose.theme.Black_item_title
import com.near.android.charg2earn.R


@Composable
fun HomeToBar(name: String = "ebatte-test.testnet") {
    ConstraintLayout(
        modifier = Modifier
            .padding(horizontal = Horizontal)
            .fillMaxWidth()
            .height(UserInfoHeight)
    ) {
        val (avatar, nftName, nftDesc) = createRefs()
        Image(painter = painterResource(R.mipmap.avatar2),
            contentDescription = "avatar",
            modifier = Modifier
                .size(avatarSize)
                .constrainAs(avatar) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                })

        Text(
            text = name,
            fontSize = NFTNameFont,
            style = textStyleBold,
            color = Black_item_title,
            modifier = Modifier.constrainAs(nftName) {
                end.linkTo(parent.end)
                top.linkTo(avatar.top)
            }
        )

        Text(
            text = stringResource(R.string.nft_owner_welcome),
            fontSize = NFTDescFont,
            style = textStyleMedium,
            color = Black_item_subtitle,
            modifier = Modifier.constrainAs(nftDesc) {
                end.linkTo(parent.end)
                bottom.linkTo(avatar.bottom)
            }
        )
    }
}

private val avatarSize = 44.dp
private val NFTNameFont = 18.sp
private val NFTDescFont = 12.sp
private val Horizontal = 20.dp
private val UserInfoHeight = 54.dp