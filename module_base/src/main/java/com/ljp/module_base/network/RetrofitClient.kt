package com.ljp.module_base.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.ljp.repository.BuildConfig
import com.ljp.repository.network.base.BaseRetrofitClient
import com.ljp.module_base.network.interceptor.HeaderInterceptor
import okhttp3.OkHttpClient


/*
 *@创建者       L_jp
 *@创建时间     2022/6/16 15:04.
 *@描述
 */
object RetrofitClient : BaseRetrofitClient() {

    /**
     * 添加需要的拦截器
     */
    override fun handleBuilder(builder: OkHttpClient.Builder) {
        builder.addInterceptor(HeaderInterceptor())
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(StethoInterceptor())
        }
    }

    override fun baseUrl(): String {
        return "https://www.wanandroid.com"
    }
}