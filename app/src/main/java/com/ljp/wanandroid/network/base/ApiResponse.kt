package com.ljp.wanandroid.network.base

import com.qszx.respository.network.BaseApiResponse


class ApiResponse<T>(val data: T? = null) : BaseApiResponse<T>() {

    private val errorCode: Int? = null
    private val errorMsg: String? = null

    override fun errorCode(): Int? {
        return errorCode
    }

    override fun errorMessage(): String? {
        return errorMsg
    }

    override fun data(): T? {
        return data
    }

    override fun isSuccessful(): Boolean {
        return errorCode == 0
    }

}

