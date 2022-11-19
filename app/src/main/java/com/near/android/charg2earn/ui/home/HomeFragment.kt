package com.near.android.charg2earn.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.near.android.charg2earn.base.back.BaseFragment
import com.near.android.charg2earn.base.composeui.DialogBoxLoading
import com.near.android.charg2earn.base.composeui.Span
import com.near.android.charg2earn.base.composeui.SpanItems
import com.near.android.charg2earn.base.composeui.singleClick
import com.near.android.charg2earn.ui.eventbus.EventMessage
import com.near.android.charg2earn.ui.home.compose.DeviceItem
import com.near.android.charg2earn.ui.home.compose.EbatteCoin
import com.near.android.charg2earn.ui.home.compose.HomeToBar
import com.near.android.charg2earn.ui.home.compose.NFTCollection
import com.near.android.charg2earn.ui.home.details.NFTDetailsActivity
import com.near.android.charg2earn.ui.home.raffle.RaffleDetailsActivity
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : BaseFragment(statusColor = White, statusBar = true, naviBar = true) {
    private val viewModel by viewModels<HomeViewModel>()

    override fun interceptBackPressed(): Boolean = true

    override fun initData() {
        EventBus.getDefault().register(this)
        viewModel.networkRequest(true)
    }

    @Composable
    override fun setComposeContent() {
        HomeUI()
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun actionEvent(message: EventMessage) {
        if (message.type == 2) {
            lifecycleScope.launch {
                viewModel.ftBalanceOf()
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Preview(showBackground = true)
    @Composable
    fun HomeUI() {
        val isProgress by viewModel.isProgress.collectAsState()

        val isRefreshing by mutableStateOf(viewModel.isRefreshing)
        val itemList by viewModel.tokensOwner.collectAsState()


        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(White)
            ) {
                HomeToBar(viewModel.accountId)

                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
                    onRefresh = {
                        viewModel.isRefreshing = true
                        viewModel.networkRequest(false)
                    }) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(ItemWidth),
                        verticalArrangement = Arrangement.spacedBy(item_paddingVer),
                        horizontalArrangement = Arrangement.spacedBy(item_padding),
                        content = {
                            item(span = Span) {
                                Column {
                                    EbatteCoin()
                                    NFTCollection(
                                        modifier = Modifier.padding(horizontal = item_padding),
                                        onClickRaffle = { RaffleDetailsActivity.startActivity(this@HomeFragment.requireActivity()) },
                                        onClickMint = {
                                            viewModel.sendNftMint()
                                        })
                                }
                            }
                            items(itemList.size, span = SpanItems) { index ->
                                val state by remember {
                                    mutableStateOf(viewModel.itemList[itemList[index].tokenId.toInt() % 3])
                                }
                                if (index % 2 == 0) {
                                    DeviceItem(
                                        modifier = Modifier
                                            .padding(start = item_padding)
                                            .singleClick {
                                                startActivity(itemList[index].metadata.title)
                                            },
                                        itemList[index],
                                        stateName = state.first,
                                        stateColor = state.second
                                    )
                                } else if (index % 2 == 1) {
                                    DeviceItem(
                                        modifier = Modifier
                                            .padding(end = item_padding)
                                            .singleClick {
                                                startActivity(itemList[index].metadata.title)
                                            },
                                        itemList[index],
                                        stateName = state.first,
                                        stateColor = state.second
                                    )
                                }
                            }
                            item(span = Span) {
                                Box(modifier = Modifier.height(bottom))
                            }
                        })
                }
            }
            if (isProgress) {
                DialogBoxLoading()
            }
        }

    }

    private fun startActivity(name: String) {
        NFTDetailsActivity.startActivity(this.requireActivity(), name)
    }
}

private val item_padding = 20.dp
private val ItemWidth = 158.dp
private val item_paddingVer = 22.dp
private val bottom = 60.dp

