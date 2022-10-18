package com.ljp.wanandroid.test.fragment.tab1

import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.network.base.ApiResponse
import com.ljp.wanandroid.test.TestRepository
import com.qszx.base.ui.BaseViewModel
import com.qszx.respository.network.BaseApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


/*
 *@创建者       L_jp
 *@创建时间     2022/10/16 16:26.
 *@描述
 */
class TabViewModel1 : BaseViewModel() {


    private val testRepository by lazy { TestRepository() }

    private val _uiState =
        MutableStateFlow<BaseApiResponse<MutableList<HomeBannerBean>>>(ApiResponse())
    var uiState = _uiState.asStateFlow()

    suspend fun getHomeBannerList() {
        _uiState.value = testRepository.getHomeBannerList()
    }

    suspend fun login(name: String, pwd: String) = testRepository.login(name, pwd)
}