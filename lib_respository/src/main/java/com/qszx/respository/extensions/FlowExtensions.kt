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
    listenerBuilder: (ResultBuilder<T>.() -> Unit)?
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


fun <T> Flow<BaseApiResponse<T>>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
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


