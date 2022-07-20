package com.ljp.wanandroid.network.api

import com.ljp.wanandroid.model.HomeArticleListBean
import com.ljp.wanandroid.model.SearchHotKeyBean
import com.ljp.wanandroid.network.base.ApiResponse
import retrofit2.http.*


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 14:46.
 *@描述
 */
interface SearchApiService {

    /**
     * 搜索热词
     */
    @GET("/hotkey/json")
    suspend fun searchHotKeyList(): ApiResponse<MutableList<SearchHotKeyBean>>

    @FormUrlEncoded
    @POST("/article/query/{page}/json")
    suspend fun search(@Path("page") page: Int, @Field("k") key: String): ApiResponse<HomeArticleListBean>

}