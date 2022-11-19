package com.near.android.charg2earn.ui.devices.details

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.near.android.charg2earn.R
import com.near.android.charg2earn.base.BaseActivity
import com.near.android.charg2earn.base.composeui.*
import com.near.android.charg2earn.ui.compose.theme.Black2
import com.near.android.charg2earn.ui.devices.details.compose.*
import com.near.android.charg2earn.ui.devices.details.viewmodel.DevicesDetailViewModel


class DevicesDetailActivity : BaseActivity(
    darkTheme = false,
    navigationBarTheme = false,
    statusBarColor = Black2,
) {
    companion object {
        fun startActivity(context: Context, deviceId: String) {
            context.startActivity(
                Intent(
                    context,
                    DevicesDetailActivity::class.java
                ).putExtra("device_id", deviceId)
            )
        }
    }


    private val viewModel by viewModels<DevicesDetailViewModel>()
    private var deviceId: String = ""

    override fun initialData() {
        deviceId = intent.getStringExtra("device_id") ?: ""
        viewModel.getRefreshing(deviceId, true)

    }

    @Composable
    override fun SetContentUI() {
        val isProgress by viewModel.isProgress.collectAsState()
        NftTopAppBar(
            title = stringResource(R.string.device_battery),
            toolBarColor = Black2,
            titleColor = Color.White,
            imageMenu = {
                CustomMenuBar(imageMenu = R.drawable.icon_left_arrow, tintColor = Color.White) {
                    this.finish()
                }
            },
            imageMore = {
                CustomMenuBar(
                    imageMenu = R.drawable.ic_device_details_history,
                    tintColor = Color.White,
                    size = 28
                ) {
                }
            }) {
            if (isProgress) {
                DialogBoxLoading()
            }
            ShowContent()
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Preview
    @Composable
    fun ShowContent() {
        val deviceRewardCount by viewModel.deviceRewardCount.collectAsState()
        val isRefreshing by mutableStateOf(viewModel.isRefreshing)

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = {
                viewModel.isRefreshing = true
                viewModel.getRefreshing(deviceId, false)
            }) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(ItemWight),
                modifier = Modifier
                    .fillMaxSize()
                    .background(Black2),
                horizontalArrangement = Arrangement.spacedBy(ItemPadding),
                content = {
                    item(span = Span) {
                        Column(Modifier.fillMaxWidth()) {
                            DeviceConnectedInfo(modifier = Modifier.padding(horizontal = DeviceInfoPadding))
                            DetailsClaim(deviceRewardCount) {
                                viewModel.claimReward(deviceId)
                            }
                        }
                    }
                    items(4, span = SpanItems) { index ->
                        if (index == 0) {
                            DeviceChangeState(
                                modifier = Modifier.padding(
                                    start = ItemPadding,
                                    top = ItemPadding
                                )
                            )
                        } else {
                            if (index % 2 == 1) {
                                DeviceWorkState(
                                    modifier = Modifier.padding(
                                        end = ItemPadding,
                                        top = ItemPadding
                                    ),
                                    viewModel.stateList[index - 1]
                                )
                            } else if (index % 2 == 0) {
                                DeviceWorkState(
                                    modifier = Modifier.padding(
                                        start = ItemPadding,
                                        top = ItemPadding
                                    ), viewModel.stateList[index - 1]
                                )
                            }
                        }
                    }
                    item(span = Span) {
                        Column(Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.height(DeviceHeight))
                            DevicesRecycleView(
                                onClickCycleNow = {
                                    viewModel.recycle(deviceId)
                                },
                                onClickSuccess = {
                                    viewModel.setDialogSuccess(false)
                                    viewModel.setRecycleDialog(false)
                                    this@DevicesDetailActivity.finish()
                                })
                        }
                    }
                })
        }
    }
}

private val ItemPadding = 20.dp
private val DeviceInfoPadding = 14.dp
private val ItemWight = 158.dp
private val DeviceHeight = 10.dp
