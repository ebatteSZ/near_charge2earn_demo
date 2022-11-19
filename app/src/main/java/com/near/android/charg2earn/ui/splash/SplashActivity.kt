package com.near.android.charg2earn.ui.splash

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.pager.*
import com.near.android.charg2earn.R
import com.near.android.charg2earn.base.BaseActivity
import com.near.android.charg2earn.base.composeui.NftTopAppBar
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.ui.MainActivity
import com.near.android.charg2earn.ui.compose.theme.*
import com.near.android.charg2earn.ui.more.CardButton
import java.util.*


class SplashActivity :
    BaseActivity(statusBarColor = Black2, darkTheme = false, navigationBarTheme = false) {

    private val viewModel by viewModels<SplashViewModel>()

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, SplashActivity::class.java))
        }
    }


    override fun initialData() {
        installSplashScreen()
    }

    @Composable
    override fun SetContentUI() {

        Sample()
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data
        if (viewModel.attemptLogin(uri)) {
            MainActivity.startActivity(this)
            this@SplashActivity.finish()
        }
    }


    @OptIn(ExperimentalPagerApi::class)
    @Composable
    private fun Sample() {
        val list = mutableStateListOf(
            R.mipmap.recycle_to_earn,
            R.mipmap.charge_to_earn
        )
        NftTopAppBar(title = "", imageMenu = {}, toolBarColor = Black2) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Black2)
            ) {
                val pagerState = rememberPagerState(pageCount = list.size)

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                ) { page ->
                    PagerSampleItem(
                        image = list[page],
                        isVisible = page == 1
                    ) {
                        viewModel.login(this@SplashActivity, "")
                    }
                }
            }
        }
    }
}

@Composable
internal fun PagerSampleItem(
    @DrawableRes image: Int,
    isVisible: Boolean = false,
    onClick: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (picture, content, button) = createRefs()
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(picture) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        if (isVisible) {
            CardButton(
                modifier = Modifier
                    .padding(
                        start = CardPaddingBottom,
                        end = CardPaddingBottom,
                        bottom = CardPaddingHorizontal
                    )
                    .height(CardHeight)
                    .fillMaxWidth()
                    .constrainAs(button) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    },
                colors = ButtonDefaults.buttonColors(GreenColor),
                onClick = {
                    onClick()
                }) {
                Text(
                    text = stringResource(id = R.string.connect),
                    color = MoreTextColor,
                    style = textStyleBold,
                    fontSize = CardTextSize
                )
            }
        }
    }
}

private val CardPaddingBottom = 20.dp
private val CardPaddingHorizontal = 36.dp
private val CardHeight = 58.dp
private val CardTextSize = 18.sp

@Preview
@Composable
fun Show() {
    Row(modifier = Modifier.fillMaxSize()) {
    PagerSampleItem(R.mipmap.recycle_to_earn) {}
    PagerSampleItem(R.mipmap.charge_to_earn,true) {}
    }
}
