package com.ljp.wanandroid.ui.activity

import android.os.Bundle
import com.ljp.wanandroid.databinding.ActivitySplashBinding
import com.ljp.wanandroid.preference.UserPreference
import com.ljp.wanandroid.ui.activity.login.LoginActivity
import com.qszx.base.ui.BaseBindingActivity
import com.qszx.utils.extensions.startActivity


/*
 *@创建者       L_jp
 *@创建时间     2022/6/29 10:19.
 *@描述
 */
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
//        initViewListener()
        startActivity<LoginActivity>()
        finish()
    }

    override fun fitsSystemWindowsStatusBarColor(): Int {
        return -1
    }

    private fun initViewListener() {

        binding.lottie.addAnimatorUpdateListener {
            lottieStatusChange(it.animatedValue.toString().toFloat())
        }
    }

    private fun lottieStatusChange(animatedValue: Float) {
        if (animatedValue != 1.0f) {
            return
        }
        if (UserPreference.isLogin()) {
            startActivity<MainActivity>()
        } else {
            startActivity<LoginActivity>()
        }
        finish()
    }

}

