package com.example.lib_imageload.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import java.io.InputStream


/*
 *@创建者       L_jp
 *@创建时间     2021/11/29 17:24.
 *@描述
 */

@GlideModule
class UnsafeOkHttpGlideModule: AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient()
        glide.registry.replace(
            GlideUrl::class.java, InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }
}