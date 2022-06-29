package com.ljp.wanandroid.network.repository

import com.qszx.respository.network.BaseRepository
import com.ljp.wanandroid.network.api.TestApiService
import com.ljp.wanandroid.network.base.RetrofitClient


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:25.
 *@描述
 */
class TestRepository : BaseRepository() {

    private val testApiService by lazy {
        RetrofitClient.getApiService(TestApiService::class.java)
    }

    suspend fun login(name: String, pwd: String) = executeHttp { testApiService.login(name, pwd) }


    suspend fun getWxarticleList() = executeHttp(testApiService::getWxarticleList)

    suspend fun getNetDataError() = executeHttp(testApiService::getNetDataError)
}