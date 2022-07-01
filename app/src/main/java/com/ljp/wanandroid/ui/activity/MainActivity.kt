package com.ljp.wanandroid.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.qszx.base.ui.BaseBindingActivity
import com.ljp.wanandroid.databinding.ActivityMainBinding
import com.ljp.wanandroid.widget.SpeedDrawerLayout
import com.qszx.utils.showToast

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        initDrawerLayout()
    }

    private fun initDrawerLayout() {
        binding.drawerLayout.setScrimColor(Color.TRANSPARENT)
        binding.button.setOnClickListener {
            if (binding.drawerLayout.isDrawerOpen(Gravity.START)) {
                binding.drawerLayout.closeDrawer(Gravity.START)
            } else {
                binding.drawerLayout.openDrawer(Gravity.START)
            }
        }
        binding.drawerLayout.addDrawerListener(object : SpeedDrawerLayout.SimpleDrawerListener() {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                binding.motionLayout.progress = slideOffset
                binding.mineLayout.getMotionLayout().progress = slideOffset
            }
        })
    }


    override fun immersionBarView(): View {
        return binding.clMainContent
    }
}
