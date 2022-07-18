package com.ljp.wanandroid.network.repository

import com.ljp.wanandroid.network.api.HomeApiService
import com.qszx.respository.network.BaseRepository
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 09:42.
 *@描述
 */
class HomeRepository @Inject constructor(private val homeApiService: HomeApiService) : BaseRepository() {

    /**
     * 首页banner
     */
    suspend fun getHomeBannerList() = executeHttp { homeApiService.getHomeBannerList() }

    /**
     * 置顶文章
     */
    suspend fun getHomeTopArticle() = executeHttp { homeApiService.getHomeTopArticle() }

    /**
     * 热门文章
     */
    suspend fun getHomeHotArticle(page: Int) = executeHttp { homeApiService.getHomeHotArticle(page) }


    /**
     * 收藏
     */
    suspend fun collect(id: Long) = executeHttp { homeApiService.collect(id) }


    /**
     * 取消收藏
     */
    suspend fun cancelCollect(id: Long) = executeHttp { homeApiService.cancelCollect(id) }


}