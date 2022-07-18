package com.qszx.base.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.qszx.base.R
import com.qszx.utils.showToast
import java.util.regex.Pattern


/*
 *@创建者       L_jp
 *@创建时间     2022/7/1 13:59.
 *@描述
 */
abstract class RouterActivity<VB : ViewBinding> : BaseBindingActivity<VB>() {


    private lateinit var navController: NavController
    private var exitTime = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    exitTime = System.currentTimeMillis()
                    showToast("再按一次返回桌面")
                } else {
                    moveTaskToBack(true)
                }
            }
        })
    }

    override fun setContentView(view: View?) {
        super.setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(controllerId())
        navController = (navHostFragment as NavHostFragment).navController
    }

    /**
     * NavController的视图id
     */
    abstract fun controllerId(): Int

    /**
     * 导航方法，根据路由名跳转
     */
    abstract fun navigation(name: String, bundle: Bundle? = null)

    fun navigate(@IdRes resId: Int, args: Bundle? = null) {
        navController.navigate(resId, args, getNavOptions())
    }

    private fun getNavOptions(): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
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
        navController.navigate(Uri.parse(newDeepLink), getNavOptions())
    }

    fun popBackStack(@IdRes destinationId: Int = 0, inclusive: Boolean = true) {
        navController.popBackStack(destinationId, inclusive)
    }

}