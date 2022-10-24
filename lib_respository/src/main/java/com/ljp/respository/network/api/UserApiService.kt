package com.ljp.respository.network.api

import com.ljp.respository.data.UserBean
import com.ljp.respository.network.base.ApiResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:22.
 *@描述
 */
interface
UserApiService {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("/user/login")
    suspend fun login(@Field("username") userName: String, @Field("password") passWord: String): ApiResponse<UserBean>

    /**
     * 注册
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun register(
        @Field("username") userName: String,
        @Field("password") passWord: String,
        @Field("repassword") repassword: String
    ): ApiResponse<UserBean>


    /**
     * 退出
     */
    @GET("/user/logout/json")
    suspend fun logout(): ApiResponse<Any>

}
