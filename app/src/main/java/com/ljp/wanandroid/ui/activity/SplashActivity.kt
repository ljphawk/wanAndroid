package com.ljp.wanandroid.ui.activity

import android.content.Intent
import android.os.Bundle
import com.ljp.wanandroid.databinding.ActivitySplashBinding
import com.ljp.wanandroid.ui.activity.test.TestActivity
import com.qszx.base.ui.BaseBindingActivity
import com.qszx.utils.extensions.startActivity


/*
 *@创建者       L_jp
 *@创建时间     2022/6/29 10:19.
 *@描述
 */
class SplashActivity : BaseBindingActivity<ActivitySplashBinding>() {

    override fun initData(savedInstanceState: Bundle?) {
        //处理首次安装点击打开切到后台,点击桌面图标再回来重启的问题及通过应用宝唤起在特定条件下重走逻辑的问题
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            // Thus finishing this will get us to the last viewed activity
            finish()
            return
        }
//        initViewListener()
        lottieStatusChange(1.0f)
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
//        startActivity<MainActivity>()
        startActivity<TestActivity>()
        finish()
    }

}

