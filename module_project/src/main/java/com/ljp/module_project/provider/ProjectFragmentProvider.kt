package com.ljp.module_project.provider

import androidx.fragment.app.Fragment
import com.ljp.module_base.router.service.IProjectFragmentService
import com.ljp.module_project.ui.fragment.ProjectFragment
import com.therouter.inject.ServiceProvider


/*
 *@创建者       L_jp
 *@创建时间     2022/11/5 10:41.
 *@描述
 */
@ServiceProvider
fun getProjectFragmentFragmentServiceImpl(): IProjectFragmentService {
    return object : IProjectFragmentService {
        override fun getFragment(): Fragment {
            return ProjectFragment.newInstance()
        }
    }
}