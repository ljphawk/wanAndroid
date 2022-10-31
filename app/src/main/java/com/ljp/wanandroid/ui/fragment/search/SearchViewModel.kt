package com.ljp.wanandroid.ui.fragment.search

import com.ljp.module_base.network.repository.SearchRepository
import com.ljp.module_base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 15:01.
 *@描述
 */
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : BaseViewModel() {


    /**
     * 搜索热词
     */
    suspend fun searchHotKeyList() = searchRepository.searchHotKeyList()

    /**
     * 搜索
     */
    suspend fun search(page: Int, key: String) = searchRepository.search(page, key)
}