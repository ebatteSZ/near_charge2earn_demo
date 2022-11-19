package com.near.android.charg2earn.ui.more

import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.near.android.charg2earn.base.BaseActivity
import com.near.android.charg2earn.ui.compose.theme.Black2

class WebViewActivity : BaseActivity(
    darkTheme = false,
    navigationBarTheme = false,
    statusBarColor = Black2,
) {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, WebViewActivity::class.java))
        }
    }

    override fun initialData() {

    }

    @Composable
    override fun SetContentUI() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black2)
        ) {
            MyContent()
        }
    }

    @Composable
    fun MyContent() {
        val webAboutEN = "https://www.ebatte.com/mission"

        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                loadUrl(webAboutEN)
            }
        }, update = {
            it.loadUrl(webAboutEN)
        })
    }
}