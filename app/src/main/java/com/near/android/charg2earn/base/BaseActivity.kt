package com.near.android.charg2earn.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.core.view.WindowCompat
import com.near.android.charg2earn.ui.compose.theme.NftApplicationTheme

abstract class BaseActivity(
    private val darkTheme: Boolean = true,
    private val navigationBarTheme: Boolean = true,
    private val statusBarColor: Color = White,
) : ComponentActivity() {

    open fun setDecorFitsSystemWindows(isDecor: Boolean) {
        WindowCompat.setDecorFitsSystemWindows(window, isDecor)
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        return
    }

    abstract fun initialData()

    @Composable
    abstract fun SetContentUI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDecorFitsSystemWindows(true)
        initialData()
        setContent {
            NftApplicationTheme(
                darkTheme = darkTheme,
                navigationBarTheme = navigationBarTheme,
                statusBarColor = statusBarColor,
                navigationBarColor = statusBarColor
            ) {
                SetContentUI()
            }
        }
    }
}
