package com.ljp.wanandroid.ui.activity.test

import com.ljp.wanandroid.model.ArticleBean
import com.ljp.wanandroid.model.WxArticleBean
import com.ljp.wanandroid.network.base.ApiResponse
import com.ljp.wanandroid.network.repository.TestRepository
import com.qszx.base.ui.BaseViewModel
import com.qszx.respository.network.BaseApiResponse
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/10/20 20:56.
 *@描述
 */

@HiltViewModel
class TestViewModel @Inject constructor(private val testRepository: TestRepository) :
    BaseViewModel() {

    private val _homeTopArticle =
        MutableStateFlow<BaseApiResponse<MutableList<ArticleBean>>>(ApiResponse())
    var homeTopArticle = _homeTopArticle.asStateFlow()

    suspend fun getHomeTopArticle() {
        _homeTopArticle.value = testRepository.getHomeTopArticle()
    }

    private val _homeTopArticle2 = MutableSharedFlow<BaseApiResponse<MutableList<ArticleBean>>>()
    var homeTopArticle2 = _homeTopArticle2.asSharedFlow()

    suspend fun getHomeTopArticle2() {
        //emit 挂起函数 tryEmit非挂起函数
        _homeTopArticle2.emit(testRepository.getHomeTopArticle())
    }

    suspend fun getHomeBannerList() = testRepository.getHomeBannerList()
}