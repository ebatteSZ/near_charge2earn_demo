package com.near.android.charg2earn.ui.more

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import com.near.android.charg2earn.base.back.BaseFragment
import com.near.android.charg2earn.base.composeui.NftTopAppBar
import com.near.android.charg2earn.R


class MoreFragment : BaseFragment(statusColor = White, statusBar = true, naviBar = true) {
    override fun interceptBackPressed(): Boolean = false

    override fun initData() {

    }

    @Composable
    override fun setComposeContent() {
        NftTopAppBar(
            title = stringResource(id = R.string.title_more),
            toolBarColor = White,
            imageMenu = { }) {
            MoreView(this@MoreFragment.requireActivity())
        }
    }
}