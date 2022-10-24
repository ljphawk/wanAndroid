package com.ljp.lib_base.callback

import androidx.lifecycle.LifecycleOwner

interface IUiView : LifecycleOwner {

    fun showLoading(loadingText: String = "加载中")

    fun dismissLoading()
}