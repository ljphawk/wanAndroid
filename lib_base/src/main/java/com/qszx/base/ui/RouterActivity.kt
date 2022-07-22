package com.qszx.base.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import ljp.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.qszx.base.R
import com.qszx.utils.CommUtils
import com.qszx.utils.showToast
import java.util.regex.Pattern


/*
 *@创建者       L_jp
 *@创建时间     2022/7/1 13:59.
 *@描述
 */
abstract class RouterActivity<VB : ViewBinding> : BaseBindingActivity<VB>() {


    lateinit var navController: NavController
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
     * 导航方法，根据路由id跳转
     */
    open fun navigate(@IdRes actionId: Int, args: Bundle? = null) {
        CommUtils.hideSoftKeyBoard(this)
        navController.navigate(actionId, args, getNavOptions())
    }

    /**
     * 导航方法，根据路由安全参数跳转
     */
    fun navigate(directions: NavDirections) {
        navigate(directions.actionId, directions.arguments)
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

    fun popBackStack(@IdRes destinationId: Int, inclusive: Boolean) {
        navController.popBackStack(destinationId, inclusive)
    }

    fun popBackStack() {
        navController.popBackStack()
    }

}