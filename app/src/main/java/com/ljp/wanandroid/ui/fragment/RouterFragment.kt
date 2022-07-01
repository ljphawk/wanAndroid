package com.ljp.wanandroid.ui.fragment

import android.content.Context
import androidx.viewbinding.ViewBinding
import com.ljp.wanandroid.ui.activity.RouterActivity
import com.qszx.base.ui.BaseBindingFragment


/*
 *@创建者       L_jp
 *@创建时间     2022/7/1 14:27.
 *@描述
 */
abstract class RouterFragment<VB : ViewBinding>:BaseBindingFragment<VB>() {


    /**
     * 获取RouterActivity方便调用navigation进行页面切换
     */
    lateinit var activity: RouterActivity<*>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as RouterActivity<*>
    }

}