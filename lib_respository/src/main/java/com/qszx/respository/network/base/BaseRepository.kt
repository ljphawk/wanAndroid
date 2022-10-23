package com.qszx.respository.network.base


/*
 *@创建者       L_jp
 *@创建时间     2022/6/13 13:14.
 *@描述
 */
open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> ApiResponse<T>): ApiResponse<T> {
        runCatching {
            block.invoke()
        }.onSuccess { data: ApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            e.printStackTrace()
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): ApiFailedResponse<T> {
        e.printStackTrace()
        return ApiFailedResponse(-1, "网络请求错误")
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: ApiResponse<T>): ApiResponse<T> {
        return if (data.isSuccessful()) {
            ApiSuccessResponse(data.data)
        } else {
            ApiFailedResponse(data.errorCode, data.errorMsg ?: "未知的错误${data.errorCode}")
        }
    }

}