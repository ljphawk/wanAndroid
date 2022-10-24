package com.ljp.wanandroid.ui.activity.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ljp.respository.data.ArticleBean
import com.ljp.respository.network.repository.TestRepository
import com.ljp.module_base.ui.BaseViewModel
import com.ljp.respository.network.base.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
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
        MutableStateFlow<ApiResponse<MutableList<ArticleBean>>>(ApiResponse())
    var homeTopArticle = _homeTopArticle.asStateFlow()

    suspend fun getHomeTopArticle() {
        _homeTopArticle.value = testRepository.getHomeTopArticle()
    }

    private val _homeTopArticle2 = MutableSharedFlow<ApiResponse<MutableList<ArticleBean>>>()
    var homeTopArticle2 = _homeTopArticle2.asSharedFlow()

    suspend fun getHomeTopArticle2() {
        //emit 挂起函数 tryEmit非挂起函数
        _homeTopArticle2.emit(testRepository.getHomeTopArticle())
    }

    suspend fun getHomeBannerList() = testRepository.getHomeBannerList()

    /**
     * liveData请求数据
     */
    private val homeHotArticle2Trigger = MutableLiveData<Int>()
    fun getHomeHotArticle2(page: Int) {
        homeHotArticle2Trigger.value = page
    }

    val homeHotArticle2 = Transformations.switchMap(homeHotArticle2Trigger) {
        testRepository.getHomeHotArticle2(it)
    }

}