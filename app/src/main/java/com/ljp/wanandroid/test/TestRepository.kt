package com.ljp.wanandroid.test

import com.ljp.wanandroid.network.api.ArticleApiService
import com.ljp.wanandroid.network.api.UserApiService
import com.ljp.wanandroid.network.base.RetrofitClient
import com.qszx.respository.network.BaseRepository


/*
 *@创建者       L_jp
 *@创建时间     2022/10/16 16:30.
 *@描述
 */
class TestRepository : BaseRepository() {

    private val articleApiService by lazy {
        RetrofitClient.getApiService(ArticleApiService::class.java)
    }
    private val userApiService by lazy {
        RetrofitClient.getApiService(UserApiService::class.java)
    }

    suspend fun login(name: String, pwd: String) = executeHttp { userApiService.login(name, pwd) }


    suspend fun getHomeBannerList() = executeHttp(articleApiService::getHomeBannerList)

    suspend fun getHomeTopArticle() = executeHttp(articleApiService::getHomeTopArticle)

    //TODO
    suspend fun getNetDataError() = executeHttp(articleApiService::getHomeTopArticle)
}