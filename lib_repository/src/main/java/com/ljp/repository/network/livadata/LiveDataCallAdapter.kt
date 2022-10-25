package com.ljp.repository.network.livadata

import com.ljp.repository.network.base.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class LiveDataCallAdapter(private val responseType:Type) : CallAdapter<ApiResponse<*>, Any> {
    override fun adapt(call: Call<ApiResponse<*>>): Any {
        return ApiResponseLiveData(call)
    }

    override fun responseType(): Type {
        return responseType
    }
}