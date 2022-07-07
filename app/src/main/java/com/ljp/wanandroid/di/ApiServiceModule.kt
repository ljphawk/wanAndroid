package com.ljp.wanandroid.di

import com.ljp.wanandroid.network.api.HomeApiService
import com.ljp.wanandroid.network.api.UserApiService
import com.ljp.wanandroid.network.base.RetrofitClient
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
    fun providesHomeApiService(): HomeApiService {
        return RetrofitClient.getApiService(HomeApiService::class.java)
    }


}