package com.ljp.module_home.provider

import androidx.fragment.app.Fragment
import com.ljp.module_base.router.service.IHomeFragmentService
import com.ljp.module_base.router.service.INavigationFragmentService
import com.ljp.module_home.ui.fragment.home.HomeFragment
import com.therouter.inject.ServiceProvider


/*
 *@创建者       L_jp
 *@创建时间     2022/11/5 20:46.
 *@描述
 */

@ServiceProvider
fun getHomeFragmentServiceImpl(): IHomeFragmentService {
    return object : IHomeFragmentService {
        override fun getFragment(): Fragment {
            return HomeFragment.newInstance()
        }
    }
}