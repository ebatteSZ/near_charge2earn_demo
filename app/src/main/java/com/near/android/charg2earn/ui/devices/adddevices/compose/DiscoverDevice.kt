package com.near.android.charg2earn.ui.devices.adddevices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.base.composeui.textStyleMedium
import com.near.android.charg2earn.ui.compose.theme.GreenColor
import com.near.android.charg2earn.R



@Composable
fun DiscoverDevice(modifier: Modifier = Modifier, onClickItem: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(top = DiscoverPaddingTop)
            .fillMaxSize()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.mipmap.add_bluetooth_scanning_finish),
            contentDescription = ""
        )
        Text(
            text = stringResource(id = R.string.device_add_devices_list),
            color = GreenColor,
            style = textStyleMedium,
            fontSize = DiscoverTextSize,
            modifier = Modifier.padding(bottom = DiscoverPaddingBottom)
        )


        LazyColumn(
            contentPadding = PaddingValues(ColumnPadding),
            verticalArrangement = Arrangement.spacedBy(ColumnPadding),
            content = {
                items(2) {
                    AddDevicesItem {
                        onClickItem()
                    }
                }
            })
    }
}

private val ColumnPadding = 20.dp
private val DiscoverTextSize = 16.sp
private val DiscoverPaddingBottom = 20.dp
private val DiscoverPaddingTop = 30.dp