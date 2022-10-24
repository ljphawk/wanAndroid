package com.ljp.respository.network.repository

import androidx.lifecycle.LiveData
import com.ljp.respository.data.ArticleListBean
import com.ljp.respository.network.api.ArticleApiService
import com.ljp.respository.network.api.UserApiService
import com.ljp.respository.network.base.ApiResponse
import com.ljp.respository.network.base.BaseRepository
import com.ljp.respository.network.livadata.NetworkResource
import com.ljp.respository.network.livadata.Resource
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

    fun getHomeHotArticle2(page:Int): LiveData<Resource<ArticleListBean>> {
        return object : NetworkResource<ArticleListBean>() {
            override fun createRequest(): LiveData<ApiResponse<ArticleListBean>> {
                return articleApiService.getHomeHotArticle2(page)

            }
        }.asLiveData()
    }
}