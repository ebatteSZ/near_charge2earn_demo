package com.near.android.charg2earn.ui.devices.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.near.android.charg2earn.R
import com.near.android.charg2earn.base.composeui.textStyleMedium


@SuppressLint("SuspiciousIndentation")
@Composable
fun DropDownMenu(
    expanded: MutableState<Boolean>,
    dropDownListener: () -> Unit,
) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(
            surface = Color.Transparent,
            onSurface = Color.Transparent,
            isLight = true
        ),
    ) {

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },

            ) {
            DropdownMenuItem(
                onClick = {
                    expanded.value = false
                    dropDownListener()
                },
                modifier = Modifier
//                    .height(MenuItemHeight)
                    .paint(painterResource(id = R.drawable.drop_bg))
            ) {
                DropdownMenuItemText()
            }
        }
    }
}

@Composable
fun DropdownMenuItemText() {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top =ItemPaddingTop )) {
        Image(
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = "",
            modifier = Modifier
                .padding(
                    end = IconPaddingEnd,
                    start = IconPaddingStart,
                    top = IconPaddingTop,
                    bottom = IconPaddingTop
                )
                .size(IconSize),
        )
        Text(
            text = stringResource(id = R.string.delete),
            fontSize = MenuTextSize,
            color = Color.White,
            style = textStyleMedium,
        )
    }
}

private val IconSize = 20.dp
private val IconPaddingEnd = 5.dp
private val IconPaddingStart = 8.dp
private val IconPaddingTop = 10.dp
private val ItemPaddingTop = 7.dp
private val MenuTextSize = 12.sp