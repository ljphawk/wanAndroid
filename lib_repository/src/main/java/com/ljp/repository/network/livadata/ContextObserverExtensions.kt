package com.ljp.repository.network.livadata

import androidx.lifecycle.Observer
import com.ljp.lib_base.callback.IUiView
import com.ljp.lib_base.utils.showToast
import com.ljp.repository.app.RepositoryApplicationDelegate


class NetDataObserver<T>(
    private var uiView: IUiView,
    private var onSuccess: (Resource<T>) -> Unit,
    private var onLoading: ((Resource<T>) -> Unit)? = null,
    private var onFailed: ((Resource<T>) -> Unit)? = null,
    private var showLoading: ((PagingInfo?) -> Boolean),
    private var loadingText: String = "加载中...",
    private var showFailedToast: Boolean = true,
) : Observer<Resource<T>> {

    private var hasShowLoad: Boolean = true

    override fun onChanged(res: Resource<T>?) {
        if (res == null) return
        when (res.status) {
            Status.SUCCESS -> {
                if (hasShowLoad) {
                    uiView.dismissLoading()
                }
                onSuccess(res)
            }
            Status.LOADING -> {
                hasShowLoad = showLoading.invoke(res.paging)
                if (hasShowLoad) {
                    uiView.showLoading(loadingText)
                }
                onLoading?.invoke(res)
            }
            Status.FAILED -> {
                if (hasShowLoad) {
                    uiView.dismissLoading()
                }
                if (showFailedToast) {
                    RepositoryApplicationDelegate.instance.showToast(res.msg)
                }
                onFailed?.invoke(res)
            }
        }
    }
}


fun <T> IUiView.createObserver(
    onSuccess: (Resource<T>) -> Unit,
    onLoading: ((Resource<T>) -> Unit)? = null,
    onFailed: ((Resource<T>) -> Unit)? = null,
    loadingText: String = "加载中...",
    showLoading: ((PagingInfo?) -> Boolean) = { true },
    showFailedToast: Boolean = true,
): Observer<Resource<T>> {
    return NetDataObserver(
        uiView = this,
        onSuccess = onSuccess,
        onLoading = onLoading,
        onFailed = onFailed,
        loadingText = loadingText,
        showLoading = showLoading,
        showFailedToast = showFailedToast
    )
}
