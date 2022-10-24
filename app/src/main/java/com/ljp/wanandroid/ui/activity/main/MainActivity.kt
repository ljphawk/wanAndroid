package com.ljp.wanandroid.ui.activity.main

import android.os.Bundle
import androidx.activity.viewModels
import com.ljp.wanandroid.R
import com.ljp.wanandroid.constant.Router
import com.ljp.wanandroid.databinding.ActivityMainBinding
import com.ljp.wanandroid.manager.SdkInitManager
import com.ljp.module_base.ui.RouterActivity
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

    override fun navigate(deepLink: String, args: Bundle?) {
        if (mainViewModel.pathNeedLogin(deepLink)) {
            super.navigate(Router.USER_LOGIN, args)
        } else {
            super.navigate(deepLink, args)
        }
    }
}
