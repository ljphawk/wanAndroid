package com.ljp.wanandroid.ui.test

import com.qszx.base.ui.BaseViewModel
import com.qszx.respository.network.BaseApiResponse
import com.ljp.wanandroid.model.WxArticleBean
import com.ljp.wanandroid.network.base.TestApiResponse
import com.ljp.wanandroid.network.repository.TestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


/*
 *@创建者       L_jp
 *@创建时间     2022/6/13 11:25.
 *@描述
 */
class TestViewModel : BaseViewModel() {

    private val testRepository = TestRepository()

    private val _uiState = MutableStateFlow<BaseApiResponse<MutableList<WxArticleBean>>>(TestApiResponse())
    var uiState = _uiState.asStateFlow()

    suspend fun getWxarticleList() {
        _uiState.value = testRepository.getWxarticleList()
    }

    suspend fun login(name: String, pwd: String) = testRepository.login(name, pwd)

    suspend fun getNetDataError() = testRepository.getNetDataError()


}