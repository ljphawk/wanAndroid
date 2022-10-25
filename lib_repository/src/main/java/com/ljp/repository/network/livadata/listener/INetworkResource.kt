package com.ljp.repository.network.livadata.listener

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import com.ljp.repository.network.base.ApiResponse

interface INetworkResource<T> {

    @MainThread
    fun createRequest(): LiveData<ApiResponse<T>>

    fun onResponseSuccess(response: ApiResponse<T>) {

    }

    fun onFailed(code: Int, msg: String?) {

    }
}