package com.ljp.wanandroid.extensions

import android.os.Bundle
import com.ljp.wanandroid.R
import com.qszx.base.ui.BaseBindingFragment


/*
 *@创建者       L_jp
 *@创建时间     2022/7/18 19:00.
 *@描述
 */

private val isNeedLoginIds = mutableSetOf<Int>(
//    R.id.action_mainFragment_to_loginFragment
)

fun BaseBindingFragment<*>.navigate(resId: Int, bundle: Bundle? = null) {
    if (isNeedLoginIds.contains(resId)) {
        routerActivity?.navigate(R.id.action_mainFragment_to_loginFragment, bundle)
    } else {
        routerActivity?.navigate(resId, bundle)
    }
}