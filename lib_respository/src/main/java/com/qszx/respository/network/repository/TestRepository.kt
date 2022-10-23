package com.qszx.respository.network.repository

import com.qszx.respository.network.api.ArticleApiService
import com.qszx.respository.network.api.UserApiService
import com.qszx.respository.network.base.BaseRepository
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/10/16 16:30.
 *@描述
 */
class TestRepository @Inject constructor(
    private val articleApiService: ArticleApiService,
    private val userApiService: UserApiService,
) : BaseRepository() {


    suspend fun login(name: String, pwd: String) = executeHttp { userApiService.login(name, pwd) }


    suspend fun getHomeBannerList() = executeHttp(articleApiService::getHomeBannerList)

    suspend fun getHomeTopArticle() = executeHttp(articleApiService::getHomeTopArticle)

    //TODO
    suspend fun getNetDataError() = executeHttp(articleApiService::getHomeTopArticle)

//    fun getHomeHotArticle2(): LiveData<Resource<ArticleListBean>> {
//        return object : NetworkResource<ArticleListBean>() {
//            override fun createRequest(): LiveData<ApiResponse<ArticleListBean>> {
//                return articleApiService.getHomeHotArticle2(1)
//
//            }
//        }.asLiveData()
//    }
}