package com.ljp.wanandroid.network.repository

import com.qszx.respository.network.BaseRepository
import com.ljp.wanandroid.network.api.UserApiService
import com.ljp.wanandroid.network.base.RetrofitClient


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:25.
 *@描述
 */
class UserRepository : BaseRepository() {

    private val userApiService by lazy {
        RetrofitClient.getApiService(UserApiService::class.java)
    }

    /**
     * 登录
     */
    suspend fun login(name: String, pwd: String) = executeHttp { userApiService.login(name, pwd) }

    /**
     * 注册
     */
    suspend fun register(name: String, pwd: String) = executeHttp { userApiService.register(name, pwd, pwd) }

    /**
     * 退出
     */
    suspend fun logout() = executeHttp { userApiService.logout() }


    suspend fun getWxarticleList() = executeHttp(userApiService::getWxarticleList)

}