package com.ljp.wanandroid.network.interceptor

import com.ljp.wanandroid.preference.UserPreference
import okhttp3.Interceptor
import okhttp3.Response


class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val newRequestBuilder = originRequest.newBuilder()

        newRequestBuilder.addHeader("Cookie", "loginUserName=${UserPreference.userLoginName}");
        newRequestBuilder.addHeader("Cookie", "loginUserPassword=${UserPreference.userLoginPassword}");


        return chain.proceed(newRequestBuilder.build())
    }

}