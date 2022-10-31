package com.ljp.module_base.network.api

import androidx.lifecycle.LiveData
import com.ljp.module_base.network.data.ArticleBean
import com.ljp.module_base.network.data.ArticleListBean
import com.ljp.module_base.network.data.HomeBannerBean
import com.ljp.repository.network.base.ApiResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 11:40.
 *@描述
 */
interface ArticleApiService {

    /**
     * 获取首页banner
     */
    @GET("/banner/json")
    suspend fun getHomeBannerList(): ApiResponse<MutableList<HomeBannerBean>>

    /**
     * 首页置顶文章
     */
    @GET("/article/top/json")
    suspend fun getHomeTopArticle(): ApiResponse<MutableList<ArticleBean>>

    /**
     * 首页热门文章
     */
    @GET("/article/list/{page}/json")
    suspend fun getHomeHotArticle(@Path("page") page: Int): ApiResponse<ArticleListBean>

    /**
     * 首页热门文章 普通方法
     */
    @GET("/article/list/{page}/json")
    fun getHomeHotArticle2(@Path("page") page: Int): LiveData<ApiResponse<ArticleListBean>>

    /**
     * 广场文章
     */
    @GET("/user_article/list/{page}/json")
    suspend fun getHomeSquareArticle(@Path("page") page: Int): ApiResponse<ArticleListBean>

    /**
     * 问答列表
     */
    @GET("/wenda/list/{page}/json")
    suspend fun getHomeQuestionArticle(@Path("page") page: Int): ApiResponse<ArticleListBean>

    /**
     * 收藏
     */
    @POST("/lg/collect/{id}/json")
    suspend fun collect(@Path("id") page: Long): ApiResponse<Any>

    /**
     * 取消收藏
     */
    @POST("/lg/uncollect_originId/{id}/json")
    suspend fun cancelCollect(@Path("id") page: Long): ApiResponse<Any>

}