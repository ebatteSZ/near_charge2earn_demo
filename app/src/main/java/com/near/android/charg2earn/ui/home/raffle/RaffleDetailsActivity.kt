package com.near.android.charg2earn.ui.home.raffle

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.near.android.charg2earn.base.BaseActivity
import com.near.android.charg2earn.base.composeui.CustomMenuBar
import com.near.android.charg2earn.base.composeui.DialogBoxLoading
import com.near.android.charg2earn.base.composeui.NftTopAppBar
import com.near.android.charg2earn.ui.home.raffle.compose.RaffleView
import com.near.android.charg2earn.R

class RaffleDetailsActivity : BaseActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, RaffleDetailsActivity::class.java))
        }
    }

    private val viewModel by viewModels<RaffleDetailsViewModel>()
    override fun initialData() {

    }

    @Composable
    override fun SetContentUI() {
        val isProgress by viewModel.isProgress.collectAsState()
        NftTopAppBar(title = stringResource(id = R.string.title_home_raffle), imageMenu = {
            CustomMenuBar(
                imageMenu = R.drawable.icon_left_arrow
            ) {
                this.finish()
            }
        }) {
            Box(modifier = Modifier.fillMaxSize()) {
                RaffleView()
                if (isProgress){
                    DialogBoxLoading()
                }
            }
        }
    }
}

