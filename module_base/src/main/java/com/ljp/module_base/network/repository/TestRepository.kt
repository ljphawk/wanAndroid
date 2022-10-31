package com.ljp.module_base.network.repository

import androidx.lifecycle.LiveData
import com.ljp.module_base.network.data.ArticleListBean
import com.ljp.module_base.network.api.ArticleApiService
import com.ljp.repository.network.api.UserApiService
import com.ljp.repository.network.base.ApiResponse
import com.ljp.repository.network.base.BaseRepository
import com.ljp.repository.network.livadata.NetworkResource
import com.ljp.repository.network.livadata.Resource
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