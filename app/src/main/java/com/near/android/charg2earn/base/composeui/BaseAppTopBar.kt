package com.near.android.charg2earn.base.composeui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NftTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = Black,
    toolBarColor: Color = White,
    imageMenu: @Composable () -> Unit,
    imageMore: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = title,
                        color = titleColor,
                        style = textStyleBold,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    imageMenu()
                },
                actions = {
                    if (imageMore != null) {
                        imageMore()
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = toolBarColor
                )
            )
        },
        content = {
            content()
        },
        containerColor = Transparent
    )
}

