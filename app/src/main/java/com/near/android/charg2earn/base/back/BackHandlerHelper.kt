package com.near.android.charg2earn.base.back

import androidx.fragment.app.*

object BackHandlerHelper {

    private fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments = fragmentManager.fragments
        fragments.iterator().forEach { childFragment ->
            if (isFragmentBackHandled(childFragment)) {
                return true
            }
        }
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false

    }

    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager)
    }

    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }


    private fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null && fragment.isVisible && fragment is FragmentBackHandler && (fragment as FragmentBackHandler).onBackPressed())
    }
}