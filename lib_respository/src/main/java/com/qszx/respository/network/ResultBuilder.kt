package com.qszx.respository.network

import com.qszx.base.BaseApplication
import com.qszx.utils.showToast


fun <T> BaseApiResponse<T>.parseData(
    listenerBuilder: (ResultBuilder<T>.() -> Unit)?,
    showErrorToast: Boolean = true
) {
    listenerBuilder?.let {
        val listener: ResultBuilder<T> = ResultBuilder<T>().also(it)
        when (this) {
            is ApiSuccessResponse -> {
                listener.onSuccess(this.data())
                listener.onComplete()
            }
            is ApiFailedResponse -> {
                listener.onFailed(this.errorCode(), this.errorMessage())
                listener.onComplete()
                if (showErrorToast) {
                    BaseApplication.instance.showToast(errorMessage())
                }
            }
            //空的Response
            //比如用StateFlow时,replay默认为1,会倒灌初始化的值
            //ShareFlow,replay默认为0
            is ApiEmptyResponse -> {}
        }
    }
}


class ResultBuilder<T> {
    var onSuccess: (data: T?) -> Unit = {}
    var onFailed: (errorCode: Int?, errorMsg: String?) -> Unit = { _, _ -> }
    var onComplete: () -> Unit = {}
}