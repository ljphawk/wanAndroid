package com.qszx.respository.network



/*
 *@创建者       L_jp
 *@创建时间     2022/6/13 13:14.
 *@描述
 */
open class BaseRepository {

    suspend fun <T> executeHttp(block: suspend () -> BaseApiResponse<T>): BaseApiResponse<T> {
        runCatching {
            block.invoke()
        }.onSuccess { data: BaseApiResponse<T> ->
            return handleHttpOk(data)
        }.onFailure { e ->
            return handleHttpError(e)
        }
        return ApiEmptyResponse()
    }

    /**
     * 非后台返回错误，捕获到的异常
     */
    private fun <T> handleHttpError(e: Throwable): BaseApiResponse<T> {
        e.printStackTrace()
        return ApiFailedResponse(-1, "网络请求错误")
    }

    /**
     * 返回200，但是还要判断isSuccess
     */
    private fun <T> handleHttpOk(data: BaseApiResponse<T>): BaseApiResponse<T> {
        return if (data.isSuccessful()) {
            ApiSuccessResponse(data)
        } else {
            ApiFailedResponse(data.errorCode(), data.errorMessage())
        }
    }

}