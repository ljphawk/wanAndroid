package com.qszx.respository.network.repository

import com.qszx.respository.network.api.ArticleApiService
import com.qszx.respository.network.base.BaseRepository
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 09:42.
 *@描述
 */
class ArticleRepository @Inject constructor(private val articleApiService: ArticleApiService) : BaseRepository() {

    /**
     * 首页banner
     */
    suspend fun getHomeBannerList() = executeHttp { articleApiService.getHomeBannerList() }

    /**
     * 置顶文章
     */
    suspend fun getHomeTopArticle() = executeHttp { articleApiService.getHomeTopArticle() }

    /**
     * 热门文章
     */
    suspend fun getHomeHotArticle(page: Int) = executeHttp { articleApiService.getHomeHotArticle(page) }


    /**
     * 广场文章
     */
    suspend fun getHomeSquareArticle(page: Int) = executeHttp { articleApiService.getHomeSquareArticle(page) }


    /**
     * 问答列表
     */
    suspend fun getHomeQuestionArticle(page: Int) = executeHttp { articleApiService.getHomeQuestionArticle(page) }


    /**
     * 收藏
     */
    suspend fun collect(id: Long) = executeHttp { articleApiService.collect(id) }


    /**
     * 取消收藏
     */
    suspend fun cancelCollect(id: Long) = executeHttp { articleApiService.cancelCollect(id) }


}