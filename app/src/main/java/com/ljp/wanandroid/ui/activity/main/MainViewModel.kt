package com.ljp.wanandroid.ui.activity.main

import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.ljp.wanandroid.R
import com.ljp.wanandroid.model.HomeTabLayoutData
import com.ljp.wanandroid.ui.fragment.home.HomeFragment
import com.ljp.wanandroid.ui.fragment.navigation.NavigationFragment
import com.ljp.wanandroid.ui.fragment.project.ProjectFragment
import com.ljp.wanandroid.ui.fragment.question.QuestionFragment
import com.qszx.base.ui.BaseViewModel
import java.util.ArrayList


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:17.
 *@描述
 */
class MainViewModel : BaseViewModel() {

    fun getHomeTabLayoutData(): ArrayList<CustomTabEntity> {
        val list = arrayListOf<CustomTabEntity>()
        list.add(HomeTabLayoutData("首页", R.drawable.icon_home_select, R.drawable.icon_home_unselect))
        list.add(HomeTabLayoutData("导航", R.drawable.icon_home_select, R.drawable.icon_home_unselect))
        list.add(HomeTabLayoutData("问答", R.drawable.icon_home_select, R.drawable.icon_home_unselect))
        list.add(HomeTabLayoutData("项目", R.drawable.icon_home_select, R.drawable.icon_home_unselect))
        return list
    }

    fun getAdapterFragmentList(): MutableList<Fragment> {
        val list = mutableListOf<Fragment>()
        list.add(HomeFragment.newInstance())
        list.add(NavigationFragment.newInstance())
        list.add(QuestionFragment.newInstance())
        list.add(ProjectFragment.newInstance())
        return list
    }
}