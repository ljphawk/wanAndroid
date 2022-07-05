package com.ljp.wanandroid.ui.activity.main

import android.os.Bundle
import android.view.View
import com.gyf.immersionbar.ImmersionBar
import com.ljp.wanandroid.R
import com.ljp.wanandroid.constant.Router
import com.ljp.wanandroid.databinding.ActivityMainBinding
import com.qszx.base.ui.RouterActivity

class MainActivity : RouterActivity<ActivityMainBinding>() {

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun controllerId(): Int {
        return R.id.nav_host_fragment
    }

    override fun navigation(name: String, bundle: Bundle?) {

    }
}
