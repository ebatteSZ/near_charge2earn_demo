package com.near.android.charg2earn.base.back

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.near.android.charg2earn.ui.compose.theme.NftApplicationTheme

abstract class BaseFragment(
    private val statusColor: Color,
    private val statusBar: Boolean = true,
    private val naviBar: Boolean = true
) : Fragment(), FragmentBackHandler {

    private var mHidden: Boolean = false

    override fun onBackPressed(): Boolean {
        return interceptBackPressed() || BackHandlerHelper.handleBackPress(this)
    }

    abstract fun interceptBackPressed(): Boolean

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        mHidden = hidden
        setStatusBarColor()
    }

    override fun onResume() {
        setStatusBarColor()
        super.onResume()
    }

    private fun setStatusBarColor() {
        if (!mHidden) {
            setThemeColor()
        }
    }

    private fun setThemeColor() {
        this.requireActivity().window.statusBarColor = statusColor.toArgb()
        this.requireActivity().window.navigationBarColor = statusColor.toArgb()
        WindowCompat.getInsetsController(this.requireActivity().window, this.requireView()).apply {
            isAppearanceLightStatusBars = statusBar
            isAppearanceLightNavigationBars = naviBar
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initData()
        return ComposeView(this.requireContext()).apply {
            setContent {
                NftApplicationTheme(darkTheme = statusBar, statusBarColor = statusColor,
                    navigationBarColor = statusColor, navigationBarTheme = naviBar) {
                    setComposeContent()
                }
            }
        }
    }

    abstract fun initData()

    @Composable
    abstract fun setComposeContent()
}