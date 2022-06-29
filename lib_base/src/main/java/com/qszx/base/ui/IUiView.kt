package com.qszx.base.ui

import androidx.lifecycle.LifecycleOwner

interface IUiView : LifecycleOwner {

    fun showLoading(loadingText: String = "加载中")

    fun dismissLoading()
}