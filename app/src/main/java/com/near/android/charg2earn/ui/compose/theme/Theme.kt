package com.near.android.charg2earn.ui.compose.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = White,
    secondary = Black,
    tertiary = Black,
    background = White
)


@Composable
fun NftApplicationTheme(
    isTransparent: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    navigationBarTheme: Boolean = isSystemInDarkTheme(),
    statusBarColor: Color = LightColorScheme.primary,
    navigationBarColor: Color = White,
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.let { windows ->
                windows.statusBarColor =
                    if (isTransparent) Color.Transparent.toArgb() else statusBarColor.toArgb()
                windows.navigationBarColor = navigationBarColor.toArgb()
                try {
                    WindowCompat.getInsetsController(windows, view).apply {
                        isAppearanceLightStatusBars = darkTheme
                        isAppearanceLightNavigationBars = navigationBarTheme
                    }
                } catch (e: Exception) {
                }
            }
        }
    }

    val fontScale = LocalDensity.current.fontScale
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val widthPixels = displayMetrics.widthPixels
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = widthPixels / 375.0f,
                    fontScale = fontScale
                )
            ) {
                content()
            }
        }
    )
}