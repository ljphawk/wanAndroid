package com.ljp.wanandroid.network.api

import com.ljp.wanandroid.model.User
import com.ljp.wanandroid.model.WxArticleBean
import com.ljp.wanandroid.network.base.TestApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:22.
 *@描述
 */
interface TestApiService {

    @FormUrlEncoded
    @POST("https://wanandroid.com/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): TestApiResponse<User>

    @POST("https://wanandroid.com/wxarticle/chapters/json")
    suspend fun getWxarticleList(): TestApiResponse<MutableList<WxArticleBean>>


    @POST("https://wanandroid.com/abc/chapters/json")
    suspend fun getNetDataError(): TestApiResponse<MutableList<WxArticleBean>>

}
