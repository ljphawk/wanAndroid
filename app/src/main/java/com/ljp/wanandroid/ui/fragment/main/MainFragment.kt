package com.ljp.wanandroid.ui.fragment.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.OnTabSelectListener
import com.ljp.wanandroid.databinding.FragmentMainBinding
import com.ljp.wanandroid.ui.activity.main.MainViewModel
import com.ljp.wanandroid.widget.SpeedDrawerLayout
import com.ljp.module_base.ui.BaseBindingFragment


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 11:28.
 *@描述
 */
class MainFragment : BaseBindingFragment<FragmentMainBinding>() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initViewListener()
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
                binding.mineLayout.getMotionLayout().progress = slideOffset
            }
        })
    }

    private fun initTabLayoutAndViewPage2() {
        binding.viewpager2.adapter =
            MainAdapter(mainViewModel.getAdapterFragmentList(), childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.tabLayout.setTabData(mainViewModel.getHomeTabLayoutData())
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

}