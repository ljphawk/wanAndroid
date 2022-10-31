package com.ljp.wanandroid.ui.fragment.articlelist

import com.ljp.module_base.network.repository.ArticleRepository
import com.ljp.module_base.ui.BaseViewModel
import com.ljp.repository.network.base.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 16:21.
 *@描述
 */
@HiltViewModel
class ArticleViewModel @Inject constructor(private val articleRepository: ArticleRepository) :
    BaseViewModel() {


    /**
     *  首页banner
     */
    suspend fun getHomeBannerList() = articleRepository.getHomeBannerList()

    /**
     * 置顶文章
     */
    suspend fun getHomeTopArticle() = articleRepository.getHomeTopArticle()


    /**
     * 热门文章
     */
    suspend fun getHomeHotArticle(page: Int) = articleRepository.getHomeHotArticle(page)


    /**
     * 广场文章
     */
    suspend fun getHomeSquareArticle(page: Int) = articleRepository.getHomeSquareArticle(page)


    /**
     * 问答列表
     */
    suspend fun getHomeQuestionArticle(page: Int) = articleRepository.getHomeQuestionArticle(page)


    /**
     * 收藏 ||  取消收藏
     */
    suspend fun collect(collect:Boolean, id: Long): ApiResponse<Any> {
        return if (collect) {
            articleRepository.cancelCollect(id)
        } else {
            articleRepository.collect(id)
        }
    }


}