package com.near.android.charg2earn.ui.devices

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.near.android.charg2earn.base.back.BaseFragment
import com.near.android.charg2earn.base.composeui.*
import com.near.android.charg2earn.ui.compose.theme.*
import com.near.android.charg2earn.ui.devices.adddevices.AddDevicesActivity
import com.near.android.charg2earn.ui.devices.compose.CO2View
import com.near.android.charg2earn.ui.devices.compose.ItemView
import com.near.android.charg2earn.ui.devices.compose.TotalAccRewards
import com.near.android.charg2earn.ui.devices.details.DevicesDetailActivity
import com.near.android.charg2earn.ui.eventbus.EventMessage
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.near.android.charg2earn.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DevicesFragment : BaseFragment(statusColor = Black2, statusBar = false, naviBar = false) {

    private val viewModel by viewModels<DevicesViewModel>()
    override fun initData() {
        EventBus.getDefault().register(this)
        viewModel.refreshDevices(true)
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun refreshDeviceList(message: EventMessage) {
        if (message.type == 1)
            lifecycleScope.launch{
                delay(500)
                viewModel.refreshDevices(true)
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Composable
    override fun setComposeContent() {
        NftTopAppBar(
            title = stringResource(id = R.string.title_devices),
            titleColor = White,
            toolBarColor = Black2,
            imageMenu = { }) {

            setContent()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    fun setContent() {
        val isProgress by viewModel.isProgress.collectAsState()
        val earningRateCount by viewModel.earningRateCount.collectAsState()
        val deviceRewardCount by viewModel.deviceRewardCount.collectAsState()


        val swipeRefreshState = rememberSwipeRefreshState(viewModel.refreshing())
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.isRefreshing()
        }) {
            Box(contentAlignment = Alignment.Center) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Black2)

                ) {
                    val (grid, CO2, header) = createRefs()
                    TotalAccRewards(
                        modifier = Modifier
                            .constrainAs(header) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        rewardCount = deviceRewardCount,
                        earningRate = earningRateCount
                    ) {
                        viewModel.setClaimReward()
                    }
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(ItemWidth),
                        contentPadding = PaddingValues(end = ListContentPaddingEnd),
                        modifier = Modifier
                            .padding(top = ListPaddingTop)
                            .constrainAs(grid) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        content = {
                            items(
                                viewModel.devicesList.size,
                                span = SpanItems
                            ) { index ->
                                ItemView(
                                    modifier = Modifier
                                        .padding(end = 0.dp)
                                        .clickable {
                                            DevicesDetailActivity.startActivity(
                                                this@DevicesFragment.requireActivity(),
                                                viewModel.devicesList[index]
                                            )
                                        }
                                ) {
                                    viewModel.unpairing(viewModel.devicesList[index])
                                }
                            }
                            if (viewModel.devicesList.size % 2 == 1) {
                                item(span = Span) {
                                    Text(text = "")
                                }
                            }
                            item(span = Span) {
                                Row(
                                    modifier = Modifier
                                        .padding(bottom = AddDevicesPaddingBottom)
                                        .fillMaxWidth()
                                        .padding(vertical = AddDevicesPaddingVertical)
                                        .clickable {
                                            AddDevicesActivity.startActivity(this@DevicesFragment.requireActivity())
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "+",
                                        fontSize = AddDevicesPlus,
                                        color = AddDeviceColor
                                    )
                                    Text(
                                        text = stringResource(id = R.string.device_add_device),
                                        fontSize = AddDevicesText,
                                        color = AddDeviceColor,
                                        modifier = Modifier.padding(start = 5.dp)
                                    )
                                }
                            }
                        })

                    CO2View(
                        modifier = Modifier
                            .constrainAs(CO2) {
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            })
                }

                if (isProgress) {
                    DialogBoxLoading()
                }
            }
        }
    }

    override fun interceptBackPressed(): Boolean = false
}

private val ItemWidth = 158.dp
private val ListPaddingTop = 175.dp
private val ListContentPaddingEnd = 20.dp
private val AddDevicesPaddingBottom = 140.dp
private val AddDevicesPaddingVertical = 30.dp
private val AddDevicesPlus = 32.sp
private val AddDevicesText = 16.sp

