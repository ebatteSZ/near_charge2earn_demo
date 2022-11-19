package com.near.android.charg2earn.ui.home.details

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import com.near.android.charg2earn.base.BaseActivity
import com.near.android.charg2earn.base.composeui.CustomMenuBar
import com.near.android.charg2earn.base.composeui.NftTopAppBar
import com.near.android.charg2earn.R


class NFTDetailsActivity : BaseActivity() {
    companion object {
        fun startActivity(context: Context, nftName: String) {
            context.startActivity(
                Intent(
                    context,
                    NFTDetailsActivity::class.java
                ).putExtra("nftName", nftName)
            )
        }
    }

    override fun initialData() {
    }

    @Composable
    override fun SetContentUI() {
        val nftName = intent.getStringExtra("nftName")
        NftTopAppBar(title = nftName!!, imageMenu = {
            CustomMenuBar(imageMenu = R.drawable.icon_left_arrow) {
                this.finish()
            }
        }) {
            NFTDetailsUI(nftName)
        }
    }

}