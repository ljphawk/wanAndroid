package com.qszx.respository.network.repository

import com.qszx.respository.network.api.SearchApiService
import com.qszx.respository.network.base.BaseRepository
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2020/8/4 16:25.
 *@描述
 */
class SearchRepository @Inject constructor(private val searchApiService: SearchApiService) : BaseRepository() {

    /**
     * 搜索热词
     */
    suspend fun searchHotKeyList() = executeHttp { searchApiService.searchHotKeyList() }


    /**
     * 搜索
     */
    suspend fun search(page: Int, key: String) = executeHttp { searchApiService.search(page, key) }


}