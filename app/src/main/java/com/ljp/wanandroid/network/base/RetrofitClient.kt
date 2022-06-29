package com.ljp.wanandroid.network.base

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.qszx.respository.network.BaseRetrofitClient
import com.ljp.wanandroid.BuildConfig
import com.ljp.wanandroid.commonkey.EnvConstant
import com.ljp.wanandroid.network.interceptor.HeaderInterceptor
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
        return EnvConstant.getApiHost()
    }
}