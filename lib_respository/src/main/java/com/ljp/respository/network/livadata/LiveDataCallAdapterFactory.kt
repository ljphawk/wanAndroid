package com.ljp.respository.network.livadata

import androidx.lifecycle.LiveData
import com.ljp.respository.network.base.ApiResponse
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class LiveDataCallAdapterFactory : CallAdapter.Factory(){

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)

        if (rawType != LiveData::class.java) {
            return null
        }

        if (returnType !is ParameterizedType) {
            throw IllegalStateException("LiveData return type must be parameterized" + " as LiveData<ApiResponse> or LiveData<? extends ApiResponse>")
        }

        val liveDataType = getParameterUpperBound(0, returnType)
        val rawLiveType = getRawType(liveDataType)

        if (rawLiveType != ApiResponse::class.java) {
            throw IllegalStateException("Only support ApiResponse.")
        }

        if (liveDataType !is ParameterizedType) {
            throw IllegalStateException("ApiResponse return type must be parameterized" + " as ApiResponse<ApiResponse> or ApiResponse<? extends ApiResponse>")
        }

        return LiveDataCallAdapter(liveDataType)
    }
}