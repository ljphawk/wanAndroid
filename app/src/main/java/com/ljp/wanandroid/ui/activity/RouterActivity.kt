package com.ljp.wanandroid.ui.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.ljp.wanandroid.R
import com.ljp.wanandroid.constant.Router
import com.qszx.base.ui.BaseBindingActivity
import java.util.regex.Pattern


/*
 *@创建者       L_jp
 *@创建时间     2022/7/1 13:59.
 *@描述
 */
abstract class RouterActivity<VB : ViewBinding> : BaseBindingActivity<VB>() {


    private lateinit var navController: NavController

    /**
     * NavController的视图id
     */
    abstract fun controllerId(): Int

    /**
     * 导航方法，根据路由名跳转
     */
    abstract fun navigation(
        name: Router,
        bundle: Bundle? = null,
    )

    override fun setContentView(view: View?) {
        super.setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(controllerId())
        navController = (navHostFragment as NavHostFragment).navController
    }

    fun navigate(@IdRes resId: Int, args: Bundle? = null) {
        navController.navigate(
            resId, args, NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
        )
    }

    /**
     * 通过正则匹配“{}”内的参数并替换
     */
    fun navigate(deepLink: String, args: Bundle? = null) {
        var newDeepLink = "http://fragment.example.com/$deepLink"
        args?.apply {
            val matcher = Pattern.compile("(\\{)(.+?)(\\})").matcher(newDeepLink)
            while (matcher.find()) {
                val key = matcher.group(2)
                if (containsKey(key)) {
                    newDeepLink = newDeepLink.replace("{$key}", get(key).toString())
                }
            }
        }
        navController.navigate(
            Uri.parse(newDeepLink), NavOptions.Builder()
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
        )
    }

    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean) {
        navController.popBackStack(destinationId, inclusive)
    }

}