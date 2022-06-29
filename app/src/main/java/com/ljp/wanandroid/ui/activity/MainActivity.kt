package com.ljp.wanandroid.ui.activity

import android.os.Bundle
import com.qszx.base.ui.BaseBindingActivity
import com.ljp.wanandroid.databinding.ActivityMainBinding

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        binding.tvContent.text = "hello word"
    }

}
