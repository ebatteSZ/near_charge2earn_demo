package com.near.android.charg2earn.ui.devices.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.near.android.charg2earn.base.composeui.singleClick
import com.near.android.charg2earn.base.composeui.textStyleBold
import com.near.android.charg2earn.R

@Composable
fun ItemView(
    modifier: Modifier = Modifier,
    dropDownListener: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Box(contentAlignment = Alignment.BottomEnd, modifier = modifier) {
        Image(
            painter = painterResource(id = R.mipmap.device_item_showes6),
            contentDescription = "",
        )
        ConstraintLayout(
            modifier = Modifier
                .size(CardSize)
                .padding(top = CardPaddingTop, start = CardPaddingTop)
        ) {
            val (Picture, Menu, Linked, Name) = createRefs()

            Image(painter = painterResource(id = R.mipmap.lifcycle_4),//pair.second),
                contentDescription = "Picture",
                modifier = Modifier
                    .height(PictureHeight)
                    .width(PictureWidth)
                    .constrainAs(Picture) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    })
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .singleClick {
                        expanded.value = false
                        expanded.value = true
                    }
                    .constrainAs(Menu) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_device_menu),
                    contentDescription = "menu",
                    modifier = Modifier
                        .padding(end = MenuPaddingEnd)
                        .align(Alignment.TopEnd)
                )
                DropDownMenu(
                    expanded,
                    dropDownListener,
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_device_linked),//if (pair.first) R.drawable.ic_device_linked else R.drawable.ic_device_unlink),
                contentDescription = "menu",
                modifier = Modifier
                    .padding(start = LinkedPaddingStart)
                    .size(LinkedSize)
                    .constrainAs(Linked) {
                        start.linkTo(Picture.end)
                        bottom.linkTo(Name.top)
                    })
            Text(text = stringResource(id = R.string.device_name),
                style = textStyleBold,
                fontSize = DeviceNameSize,
                color = White,
                modifier = Modifier
                    .padding(start = DeviceNamePaddingStart, top = DeviceNamePaddingTop)
                    .width(DeviceNameWidth)
                    .constrainAs(Name) {
                        start.linkTo(Picture.end)
                        bottom.linkTo(Picture.bottom)
                    })
        }
    }
}

private val PictureHeight = 110.dp
private val PictureWidth = 51.dp
private val MenuPaddingEnd = 14.dp
private val LinkedPaddingStart = 10.dp
private val LinkedSize = 28.dp
private val DeviceNameSize = 16.sp
private val DeviceNamePaddingStart = 10.dp
private val DeviceNamePaddingTop = 8.dp
private val DeviceNameWidth = 58.dp
private val CardSize = 165.dp
private val CardPaddingTop = 20.dp
