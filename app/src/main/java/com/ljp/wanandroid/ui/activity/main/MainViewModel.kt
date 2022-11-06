package com.ljp.wanandroid.ui.activity.main

import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.ljp.module_base.router.service.IHomeFragmentService
import com.ljp.wanandroid.R
import com.ljp.wanandroid.data.HomeTabLayoutData
import com.ljp.module_project.ui.fragment.ProjectFragment
import com.ljp.module_question.ui.fragment.QuestionFragment
import com.ljp.module_base.ui.BaseViewModel
import com.ljp.module_base.router.service.INavigationFragmentService
import com.ljp.module_base.router.service.IProjectFragmentService
import com.ljp.module_base.router.service.IQuestionFragmentService
import com.therouter.TheRouter


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:17.
 *@描述
 */
class MainViewModel : BaseViewModel() {


    fun getHomePageData(): Pair<ArrayList<CustomTabEntity>, MutableList<Fragment>> {
        val tabList = arrayListOf<CustomTabEntity>()
        val fragmentList = mutableListOf<Fragment>()
        TheRouter.get(IHomeFragmentService::class.java)?.getFragment()?.let {
            tabList.add(HomeTabLayoutData("首页",
                R.drawable.icon_home_select,
                R.drawable.icon_home_unselect))
            fragmentList.add(it)
        }
        TheRouter.get(INavigationFragmentService::class.java)?.getFragment()?.let {
            tabList.add(HomeTabLayoutData("导航",
                R.drawable.icon_home_select,
                R.drawable.icon_home_unselect))
            fragmentList.add(it)
        }
        TheRouter.get(IQuestionFragmentService::class.java)?.getFragment()?.let {
            tabList.add(HomeTabLayoutData("问答",
                R.drawable.icon_home_select,
                R.drawable.icon_home_unselect))
            fragmentList.add(it)
        }
        TheRouter.get(IProjectFragmentService::class.java)?.getFragment()?.let {
            tabList.add(HomeTabLayoutData("项目",
                R.drawable.icon_home_select,
                R.drawable.icon_home_unselect))
            fragmentList.add(it)
        }
        return Pair(tabList, fragmentList)
    }

}