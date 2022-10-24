package com.ljp.wanandroid.ui.activity.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ljp.wanandroid.ui.fragment.test.tab1.TabFragment1
import com.ljp.wanandroid.ui.fragment.test.TabFragment2
import com.ljp.wanandroid.ui.fragment.test.TabFragment3
import com.google.android.material.tabs.TabLayoutMediator
import com.ljp.wanandroid.databinding.ActivityTestBinding
import com.ljp.wanandroid.ui.activity.main.MainActivity
import com.ljp.module_base.ui.BaseBindingActivity
import com.ljp.lib_base.extensions.noQuickClick
import com.ljp.lib_base.extensions.startActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestActivity : BaseBindingActivity<ActivityTestBinding>() {

    override fun immersionBarView(): View {
        return binding.titleBar
    }

    override fun initData(savedInstanceState: Bundle?) {
        binding.titleBar.title = "测试页面"
        binding.titleBar.leftView.text = "返回首页"
        binding.titleBar.leftView.noQuickClick {
            startActivity<MainActivity>()
            finish()
        }
        initTabLayoutAndViewPager2()
    }

    private fun initTabLayoutAndViewPager2() {
        val tabList = mutableListOf("数据操作", "图片展示", "dialog")
        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(TabFragment1())
        fragmentList.add(TabFragment2())
        fragmentList.add(TabFragment3())
        binding.viewpager2.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int {
                    return fragmentList.size
                }

                override fun createFragment(position: Int): Fragment {
                    return fragmentList[position]
                }
            }
        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

}