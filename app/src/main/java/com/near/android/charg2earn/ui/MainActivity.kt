package com.near.android.charg2earn.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.near.android.charg2earn.R
import com.near.android.charg2earn.databinding.ActivityMainBinding
import com.near.android.charg2earn.base.back.BackHandlerHelper.handleBackPress
import com.near.android.charg2earn.ui.devices.DevicesFragment
import com.near.android.charg2earn.ui.home.HomeFragment
import com.near.android.charg2earn.ui.more.MoreFragment
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : AppCompatActivity() {
    private val FRAGMENT_DEVICES = 1
    private val FRAGMENT_HOME = 0
    private val FRAGMENT_MORE = 2

    private var homeFragment: HomeFragment? = null
    private var devicesFragment: DevicesFragment? = null
    private var moreFragment: MoreFragment? = null
    private var selectorIndex = 0

    private lateinit var binding: ActivityMainBinding
    val viewModel by viewModels<MainViewModel>()


    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> showFragment(FRAGMENT_HOME)
                R.id.navigation_devices -> showFragment(FRAGMENT_DEVICES)
                R.id.navigation_more -> showFragment(FRAGMENT_MORE)
            }
            true
        }
        showFragment(FRAGMENT_HOME)
        clearToast(binding.navigation)
    }


    private fun clearToast(bottomNavigationView: BottomNavigationView) {
        val ids = ArrayList<Int>()
        ids.add(R.id.navigation_home)
        ids.add(R.id.navigation_devices)
        ids.add(R.id.navigation_more)
        val bottomNavigationMenuView = bottomNavigationView.getChildAt(0) as ViewGroup
        //遍历子View,重写长按点击事件
        for (position in 0 until ids.size) {
            bottomNavigationMenuView.getChildAt(position).findViewById<View>(ids[position])
                .setOnLongClickListener { true }
        }
    }

    //显示
    private fun showFragment(index: Int) {
        selectorIndex = index
        val fragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        hideFragment(transaction)
        when (index) {
            FRAGMENT_DEVICES -> if (devicesFragment == null) {
                devicesFragment = DevicesFragment()
                transaction.add(R.id.content, devicesFragment!!)
            } else {
                transaction.show(devicesFragment!!)
            }
            FRAGMENT_HOME -> if (homeFragment == null) {
                homeFragment = HomeFragment()
                transaction.add(R.id.content, homeFragment!!)
            } else {
                transaction.show(homeFragment!!)
            }
            FRAGMENT_MORE -> if (moreFragment == null) {
                moreFragment = MoreFragment()
                transaction.add(R.id.content, moreFragment!!)
            } else {
                transaction.show(moreFragment!!)
            }
        }
        transaction.commit()
    }

    //隐藏
    private fun hideFragment(transaction: FragmentTransaction) {
        if (devicesFragment != null) {
            transaction.hide(devicesFragment!!)
        }
        if (homeFragment != null) {
            transaction.hide(homeFragment!!)
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment!!)
        }
    }

    override fun onBackPressed() {
        if (!handleBackPress(this)) {
            binding.navigation.selectedItemId = R.id.navigation_home
            showFragment(FRAGMENT_HOME)
        } else {
            super.onBackPressed()
        }
    }
}