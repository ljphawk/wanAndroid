package com.qszx.respository.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.qszx.base.ui.IUiView
import com.qszx.respository.network.ResultBuilder
import com.qszx.respository.network.BaseApiResponse
import com.qszx.respository.network.parseData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * 只发起请求,不返回结果.
 * 监听结果请根据需求 使用StateFlow或者ShareFlow
 */
fun IUiView.launch(showLoading: Boolean = true, requestBlock: suspend () -> Unit) {
    lifecycleScope.launch {
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
    requestBlock: suspend () -> BaseApiResponse<T>,
    showLoading: Boolean = true,
    showErrorToast: Boolean = true,
    listenerBuilder: (ResultBuilder<T>.() -> Unit)?,
) {
    lifecycleScope.launch {
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
    requestBlock1: suspend () -> BaseApiResponse<T1>,
    requestBlock2: suspend () -> BaseApiResponse<T2>,
    listener: (BaseApiResponse<T1>, BaseApiResponse<T2>) -> BaseApiResponse<R>,
    showLoading: Boolean = true,
    showErrorToast: Boolean = true,
    listenerBuilder: (ResultBuilder<R>.() -> Unit)?,
) {
    lifecycleScope.launch {
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


fun <T> Flow<BaseApiResponse<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.CREATED,
    showErrorToast: Boolean = true,
    listenerBuilder: ResultBuilder<T>.() -> Unit,
) {
    if (owner is Fragment) {
        owner.viewLifecycleOwner.lifecycleScope.launch {
            owner.viewLifecycleOwner.repeatOnLifecycle(minActiveState) {
                collect { apiResponse: BaseApiResponse<T> ->
                    apiResponse.parseData(listenerBuilder, showErrorToast)
                }
            }
        }
    } else {
        owner.lifecycleScope.launch {
            owner.repeatOnLifecycle(minActiveState) {
                collect { apiResponse: BaseApiResponse<T> ->
                    apiResponse.parseData(listenerBuilder, showErrorToast)
                }
            }
        }
    }
}


