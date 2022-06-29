package com.ljp.wanandroid.network.base

import com.qszx.respository.network.BaseApiResponse


class ApiResponse<T> : BaseApiResponse<T>() {

    private val data: T? = null
    private val error_code: Int? = null
    private val message: String? = null
    private val result: Int? = null

    override fun errorCode(): Int? {
        return error_code
    }

    override fun errorMessage(): String? {
        return message
    }

    override fun data(): T? {
        return data
    }

    override fun isSuccessful(): Boolean {
        return result == 0
    }

}

