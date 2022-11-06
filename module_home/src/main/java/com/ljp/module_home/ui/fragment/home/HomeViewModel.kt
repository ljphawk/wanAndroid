package com.ljp.module_home.ui.fragment.home

import androidx.fragment.app.Fragment
import com.ljp.module_base.ui.BaseViewModel
import com.ljp.module_home.ui.fragment.articlelist.ArticleListFragment
import com.ljp.module_home.ui.fragment.articlelist.ArticleListParams
import com.ljp.module_home.ui.fragment.articlelist.HomeArticleType


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 16:21.
 *@描述
 */
class HomeViewModel : BaseViewModel() {

    fun getTabLayoutData(): Array<String> {
        return arrayOf("热门", "广场", "问答")
    }

    fun getAdapterFragmentList(): MutableList<Fragment> {
        val list = mutableListOf<Fragment>()
        list.add(ArticleListFragment.newInstance(ArticleListParams.getHomeParams(HomeArticleType.HOT)))
        list.add(ArticleListFragment.newInstance(ArticleListParams.getHomeParams(HomeArticleType.SQUARE)))
        list.add(ArticleListFragment.newInstance(ArticleListParams.getHomeParams(HomeArticleType.QUESTION)))
        return list
    }


}