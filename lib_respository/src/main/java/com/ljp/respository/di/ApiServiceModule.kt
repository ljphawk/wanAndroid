package com.ljp.respository.di

import com.ljp.respository.network.api.ArticleApiService
import com.ljp.respository.network.api.SearchApiService
import com.ljp.respository.network.api.UserApiService
import com.ljp.respository.network.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/*
 *@创建者       L_jp
 *@创建时间     6/4/21 4:01 PM.
 *@描述
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun providesUserApiService(): UserApiService {
        return RetrofitClient.getApiService(UserApiService::class.java)
    }


    @Provides
    @Singleton
    fun providesArticleApiService(): ArticleApiService {
        return RetrofitClient.getApiService(ArticleApiService::class.java)
    }


    @Provides
    @Singleton
    fun providesSearchApiService(): SearchApiService {
        return RetrofitClient.getApiService(SearchApiService::class.java)
    }


}