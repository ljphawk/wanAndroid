package com.ljp.module_navigation.provider

import androidx.fragment.app.Fragment
import com.ljp.module_base.router.service.INavigationFragmentService
import com.ljp.module_navigation.ui.fragment.NavigationFragment
import com.therouter.inject.ServiceProvider


/*
 *@创建者       L_jp
 *@创建时间     2022/11/5 10:41.
 *@描述
 */
@ServiceProvider
fun getNavigationFragmentServiceImpl(): INavigationFragmentService {
    return object : INavigationFragmentService {
        override fun getFragment(): Fragment {
            return NavigationFragment.newInstance()
        }
    }
}