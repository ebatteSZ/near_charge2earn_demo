package com.near.android.charg2earn.ui.more

import android.app.Activity
import androidx.annotation.IdRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.MRoundedShape
import com.near.android.charg2earn.base.composeui.singleClick
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.compose.theme.MoreDividerColor
import com.near.android.charg2earn.ui.compose.theme.MoreTextColor
import com.near.android.charg2earn.ui.splash.SplashActivity
import com.near.android.charg2earn.R

@Composable
fun MoreView(context: Activity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = PaddingBottom)
            .verticalScroll(rememberScrollState())
    ) {
        ItemView(R.string.title_more_about) {
            WebViewActivity.startActivity(context)
        }
        ItemDriver()
        ItemView(R.string.title_more_blog) {

        }
        ItemDriver()
        ItemView(R.string.title_more_help_center) {}
        ItemDriver()
        Box(modifier = Modifier.height(ItemViewHeight), contentAlignment = Alignment.CenterStart) {
            Text(
                text = stringResource(id = R.string.title_more_follow_us),
                fontSize = ItemTitleSize,
                color = MoreTextColor,
                style = textStyleBold,
            )
        }
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CardButton(
                onClick = { }, modifier = Modifier
                    .weight(1f)
                    .height(DiscordHeight)
                    .width(DiscordWidth)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.more_twitter),
                    contentDescription = "twitter",
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(modifier = Modifier.width(DividerWidth))
            CardButton(
                onClick = { },
                modifier = Modifier
                    .weight(1f)
                    .height(DiscordHeight)
                    .width(DiscordWidth)
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.more_discord),
                    contentDescription = "discord",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        ItemDriver(modifier = Modifier.padding(vertical = RowPaddingBottom))
        CardButton(
            modifier = Modifier
                .padding(bottom = CardPaddingBottom)
                .height(CardHeight)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(MoreDividerColor),
            onClick = {
                SplashActivity.startActivity(context)
                context.finish()

            }) {
            Text(
                text = stringResource(id = R.string.title_more_sign_out),
                color = MoreTextColor,
                style = textStyleBold,
                fontSize = CardTextSize
            )
        }
    }
}

private val DividerWidth = 19.dp
private val DiscordHeight = 120.dp
private val DiscordWidth = 158.dp
private val CardPaddingBottom = 100.dp
private val CardHeight = 58.dp
private val CardTextSize = 16.sp
private val PaddingBottom = 20.dp
private val RowPaddingBottom = 40.dp

@Composable
fun ItemDriver(modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(DividerHeight)
            .background(MoreDividerColor, shape = RoundedCornerShape(DividerShape))
    )
}

private val DividerShape = 20.dp
private val DividerHeight = 1.dp

@Composable
fun ItemView(@IdRes title: Int, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .height(ItemViewHeight)
            .fillMaxWidth()
            .singleClick { onClick() }
    ) {
        val (text, arrow) = createRefs()
        Text(
            text = stringResource(id = title),
            fontSize = ItemTitleSize,
            color = MoreTextColor,
            style = textStyleBold,
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            })
        Icon(
            painter = painterResource(id = R.drawable.icon_right_arrow),
            contentDescription = "arrow",
            modifier = Modifier.constrainAs(arrow) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            })
    }
}

private val ItemViewHeight = 80.dp
private val ItemTitleSize = 18.sp


@Composable
fun CardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        shape = MRoundedShape,
        elevation = ButtonDefaults.buttonElevation(0.dp),
        contentPadding = PaddingValues(0.dp),
        colors = colors
    ) {
        content()
    }
}