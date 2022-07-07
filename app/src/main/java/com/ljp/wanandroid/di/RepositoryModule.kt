package com.ljp.wanandroid.di

import com.ljp.wanandroid.network.api.HomeApiService
import com.ljp.wanandroid.network.api.UserApiService
import com.ljp.wanandroid.network.repository.HomeRepository
import com.ljp.wanandroid.network.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


/*
 *@创建者       L_jp
 *@创建时间     6/4/21 1:35 PM.
 *@描述
 */
@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideUserRepository(userApiService: UserApiService): UserRepository {
        return UserRepository(userApiService)
    }

    @Provides
    @ViewModelScoped
    fun provideHomeRepository(homeApiService: HomeApiService): HomeRepository {
        return HomeRepository(homeApiService)
    }

}