package com.ljp.wanandroid.model

import com.flyco.tablayout.listener.CustomTabEntity


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:15.
 *@描述
 */
class HomeTabLayoutData(private val title: String, private val selectRes: Int, private val unselectIcon: Int) :
    CustomTabEntity {
    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectRes
    }

    override fun getTabUnselectedIcon(): Int {
        return unselectIcon
    }
}

