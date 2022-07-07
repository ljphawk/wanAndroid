package com.ljp.wanandroid.network.api

import com.ljp.wanandroid.model.HomeArticleBean
import com.ljp.wanandroid.model.HomeArticleListBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.network.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 11:40.
 *@描述
 */
interface HomeApiService {

    /**
     * 获取首页banner
     */
    @GET("/banner/json")
    suspend fun getHomeBannerList(): ApiResponse<MutableList<HomeBannerBean>>

    /**
     * 首页置顶文章
     */
    @GET("/article/top/json")
    suspend fun getHomeTopArticle(): ApiResponse<MutableList<HomeArticleBean>>

    /**
     * 首页热门文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeHotArticle(@Path("page") page: Int): ApiResponse<HomeArticleListBean>

}