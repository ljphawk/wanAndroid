package com.ljp.respository.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.ljp.lib_base.callback.IUiView
import com.ljp.respository.network.base.ApiResponse
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 只发起请求,不返回结果.
 * 监听结果请根据需求 使用StateFlow或者ShareFlow
 */
fun IUiView.launch(showLoading: Boolean = true, requestBlock: suspend () -> Unit) {
    getScope().launch {
        flow {
            emit(requestBlock())
        }.onStart {
            if (showLoading) {
                showLoading()
            }
        }.onCompletion {
            dismissLoading()
        }.collect()
    }
}

/**
 * 启动网络请求 loading 并收集结果
 */
fun <T> IUiView.launchAndCollect(
    requestBlock: suspend () -> ApiResponse<T>,
    showLoading: Boolean = true,
    showErrorToast: Boolean = true,
    listenerBuilder: (ResultBuilder<T>.() -> Unit)?,
) {
    getScope().launch {
        flow {
            emit(requestBlock())
        }.onStart {
            if (showLoading) {
                showLoading()
            }
        }.onCompletion {
            dismissLoading()
        }.collect {
            it.parseData(listenerBuilder, showErrorToast)
        }
    }
}

fun <T1, T2, R> IUiView.launchZipAndCollect(
    requestBlock1: suspend () -> ApiResponse<T1>,
    requestBlock2: suspend () -> ApiResponse<T2>,
    listener: (ApiResponse<T1>, ApiResponse<T2>) -> ApiResponse<R>,
    showLoading: Boolean = true,
    showErrorToast: Boolean = true,
    listenerBuilder: (ResultBuilder<R>.() -> Unit)?,
) {
    getScope().launch {
        flow { emit(requestBlock1()) }
            .zip(flow { emit(requestBlock2()) }) { t1, t2 ->
                listener.invoke(t1, t2)
            }.onStart {
                if (showLoading) {
                    showLoading()
                }
            }.onCompletion {
                dismissLoading()
            }.collect {
                it.parseData(listenerBuilder, showErrorToast)
            }
    }
}

private fun IUiView.getScope(): LifecycleCoroutineScope {
    return if (this is Fragment) {
        viewLifecycleOwner.lifecycleScope
    } else {
        lifecycleScope
    }
}


fun <T> Flow<ApiResponse<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    showErrorToast: Boolean = true,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    if (owner is Fragment) {
        owner.viewLifecycleOwner.lifecycleScope.launch {
            owner.viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
                collect { apiResponse: ApiResponse<T> ->
                    apiResponse.parseData(listenerBuilder, showErrorToast)
                }
            }
        }
    } else {
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(minActiveState) {
                collect { apiResponse: ApiResponse<T> ->
                    apiResponse.parseData(listenerBuilder, showErrorToast)
                }
            }
        }
    }
}


