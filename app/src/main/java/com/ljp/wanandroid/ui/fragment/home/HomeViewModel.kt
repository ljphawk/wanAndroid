package com.ljp.wanandroid.ui.fragment.home

import androidx.fragment.app.Fragment
import com.ljp.wanandroid.ui.fragment.articlelist.*
import com.qszx.base.ui.BaseViewModel


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
        list.add(ArticleListFragment.newInstance(ArticleListParams(HomeArticleParams(HomeArticleType.HOT))))
        list.add(ArticleListFragment.newInstance(ArticleListParams(HomeArticleParams(HomeArticleType.SQUARE))))
        list.add(ArticleListFragment.newInstance(ArticleListParams(HomeArticleParams(HomeArticleType.QUESTION))))
        return list
    }


}