package com.qszx.respository.network


/*
 *@创建者       L_jp
 *@创建时间     2022/6/16 13:19.
 *@描述
 */
abstract class BaseApiResponse<T>() {

    abstract fun errorCode(): Int?

    abstract fun errorMessage(): String?

    abstract fun data(): T?

    abstract fun isSuccessful(): Boolean

}


data class ApiSuccessResponse<T>(private val response: BaseApiResponse<T>) : BaseApiResponse<T>() {
    override fun data(): T? {
        return response.data()
    }

    override fun isSuccessful(): Boolean {
        return response.isSuccessful()
    }

    override fun errorCode(): Int? {
        return response.errorCode()
    }

    override fun errorMessage(): String? {
        return response.errorMessage()
    }
}

data class ApiFailedResponse<T>(private val error_code: Int?, private val message: String?) :
    BaseApiResponse<T>() {
    override fun errorCode(): Int? {
        return error_code
    }

    override fun errorMessage(): String? {
        return message
    }

    override fun isSuccessful(): Boolean {
        return false
    }

    override fun data(): T? {
        return null
    }
}

class ApiEmptyResponse<T> : BaseApiResponse<T>() {

    override fun isSuccessful(): Boolean {
        return false
    }

    override fun errorCode(): Int? {
        return null
    }

    override fun errorMessage(): String? {
        return null
    }

    override fun data(): T? {
        return null
    }

}