package com.ljp.wanandroid.glide

import android.graphics.drawable.Drawable
import android.view.View

interface ImageLoadingListener {

    fun onLoadingFailed(
        imageUri: String?,
        view: View?,
        cause: Throwable?
    )


    fun onLoadingComplete(
        imageUri: String?,
        view: View?,
        loadedImage: Drawable?
    )


}