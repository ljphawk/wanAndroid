package com.ljp.respository.extensions

import com.ljp.respository.network.base.*
import com.ljp.lib_base.utils.showToast
import com.ljp.respository.app.RepositoryApplicationDelegate


fun <T> ApiResponse<T>.parseData(
    listenerBuilder: (ResultBuilder<T>.() -> Unit)?,
    showErrorToast: Boolean = true,
) {
    listenerBuilder?.let {
        val listener: ResultBuilder<T> = ResultBuilder<T>().also(it)
        when (this) {
            is ApiSuccessResponse -> {
                listener.onSuccess(data)
                listener.onComplete()
            }
            is ApiFailedResponse -> {
                listener.onFailed(errorCode, errorMsg)
                listener.onComplete()
                if (showErrorToast) {
                    RepositoryApplicationDelegate.instance.showToast(errorMsg)
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