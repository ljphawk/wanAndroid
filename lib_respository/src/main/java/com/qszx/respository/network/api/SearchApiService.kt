package com.qszx.respository.network.api

import com.qszx.respository.data.ArticleListBean
import com.qszx.respository.data.SearchHotKeyBean
import com.qszx.respository.network.base.ApiResponse
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
    suspend fun search(@Path("page") page: Int, @Field("k") key: String): ApiResponse<ArticleListBean>

}