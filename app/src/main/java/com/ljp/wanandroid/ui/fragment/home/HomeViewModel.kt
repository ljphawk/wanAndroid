package com.ljp.wanandroid.ui.fragment.home

import androidx.fragment.app.Fragment
import com.ljp.wanandroid.model.HomeArticleListBean
import com.ljp.wanandroid.network.base.ApiResponse
import com.ljp.wanandroid.network.repository.HomeRepository
import com.ljp.wanandroid.ui.fragment.hot.HotFragment
import com.ljp.wanandroid.ui.fragment.project.ProjectFragment
import com.ljp.wanandroid.ui.fragment.square.SquareFragment
import com.qszx.base.ui.BaseViewModel
import com.qszx.respository.network.BaseApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 16:21.
 *@描述
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    BaseViewModel() {


    fun getTabLayoutData(): Array<String> {
        return arrayOf("热门", "广场", "问答")
    }

    fun getAdapterFragmentList(): MutableList<Fragment> {
        val list = mutableListOf<Fragment>()
        list.add(HotFragment.newInstance())
        list.add(SquareFragment.newInstance())
        list.add(ProjectFragment.newInstance())
        return list
    }


    /**
     *  首页banner
     */
    suspend fun getHomeBannerList() = homeRepository.getHomeBannerList()

    /**
     * 置顶文章
     */
    suspend fun getHomeTopArticle() = homeRepository.getHomeTopArticle()


    /**
     * 热门文章
     */
    suspend fun getHomeHotArticle(page: Int) = homeRepository.getHomeHotArticle(page)


}