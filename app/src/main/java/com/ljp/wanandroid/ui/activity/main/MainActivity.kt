package com.ljp.wanandroid.ui.activity.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.OnTabSelectListener
import com.ljp.lib_base.utils.showToast
import com.ljp.module_base.router.RouterAction
import com.ljp.module_base.router.RouterPath
import com.ljp.module_base.ui.BaseBindingActivity
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.ActivityMainBinding
import com.ljp.wanandroid.manager.SdkInitManager
import com.ljp.wanandroid.widget.SpeedDrawerLayout
import com.therouter.TheRouter
import com.therouter.router.action.interceptor.ActionInterceptor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    private var exitTime = 0L
    private val mainViewModel by viewModels<MainViewModel>()
    private val switchDrawerLayoutActionInterceptor = object : ActionInterceptor() {
        override fun handle(context: Context, args: Bundle): Boolean {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    exitTime = System.currentTimeMillis()
                    showToast("再按一次返回桌面")
                } else {
                    moveTaskToBack(true)
                }
            }
        })
    }

    override fun initData(savedInstanceState: Bundle?) {
        SdkInitManager.init()
        initViewListener()
        initMineFragment()
        TheRouter.addActionInterceptor(RouterAction.ACTION_SWITCH_DRAWER_LAYOUT,
            switchDrawerLayoutActionInterceptor)
    }

    private fun initMineFragment() {
        val let = TheRouter.build(RouterPath.PATH_MINE).createFragment<Fragment>()

        let?.let {
                supportFragmentManager.beginTransaction().add(R.id.fl_container, it).commit()
            }
    }


    private fun initViewListener() {
        initDrawerLayout()
        initTabLayoutAndViewPage2()
    }

    private fun initDrawerLayout() {
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        binding.drawerLayout.addDrawerListener(object : SpeedDrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                binding.motionLayout.progress = slideOffset
                TheRouter.build(RouterAction.ACTION_DRAWER_LAYOUT_PROGRESS)
                    .withFloat("progress", slideOffset).navigation()
            }
        })
    }

    private fun initTabLayoutAndViewPage2() {
        val pageData = mainViewModel.getHomePageData()

        binding.viewpager2.adapter =
            MainAdapter(pageData.second, supportFragmentManager, lifecycle)
        binding.tabLayout.setTabData(pageData.first)
        binding.viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                if (binding.tabLayout.currentTab != position) {
                    binding.tabLayout.currentTab = position
                }
            }
        })
        binding.tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                binding.viewpager2.setCurrentItem(position, false)
                binding.viewpager2.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        TheRouter.removeActionInterceptor(RouterAction.ACTION_SWITCH_DRAWER_LAYOUT,
            switchDrawerLayoutActionInterceptor)
    }
}
