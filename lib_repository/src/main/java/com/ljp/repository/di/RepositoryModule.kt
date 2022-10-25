package com.ljp.repository.di

import com.ljp.repository.network.api.ArticleApiService
import com.ljp.repository.network.api.SearchApiService
import com.ljp.repository.network.api.UserApiService
import com.ljp.repository.network.repository.ArticleRepository
import com.ljp.repository.network.repository.SearchRepository
import com.ljp.repository.network.repository.TestRepository
import com.ljp.repository.network.repository.UserRepository
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
    fun provideArticleRepository(articleApiService: ArticleApiService): ArticleRepository {
        return ArticleRepository(articleApiService)
    }

    @Provides
    @ViewModelScoped
    fun provideSearchRepository(searchApiService: SearchApiService): SearchRepository {
        return SearchRepository(searchApiService)
    }

    @Provides
    @ViewModelScoped
    fun provideTestRepository(articleApiService: ArticleApiService,userApiService: UserApiService): TestRepository {
        return TestRepository(articleApiService,userApiService)
    }

}