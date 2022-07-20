package com.ljp.wanandroid.ui.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.ActivityMainBinding
import com.ljp.wanandroid.manager.SdkInitManager
import com.qszx.base.ui.RouterActivity
import com.qszx.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : RouterActivity<ActivityMainBinding>() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun initData(savedInstanceState: Bundle?) {
        SdkInitManager.init()
    }

    override fun controllerId(): Int {
        return R.id.nav_host_fragment
    }

    override fun navigate(actionId: Int, args: Bundle?) {
        if (mainViewModel.actionIdNeedLogin(actionId)) {
            super.navigate(R.id.action_mainFragment_to_loginFragment, args)
        } else {
            super.navigate(actionId, args)
        }
    }
}
