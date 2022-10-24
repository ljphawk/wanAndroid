package com.ljp.respository.network.livadata

import androidx.lifecycle.LiveData
import com.ljp.respository.network.base.ApiResponse
import com.ljp.lib_base.utils.LOG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ApiResponseLiveData(private val originCall: Call<ApiResponse<*>>) :
    LiveData<ApiResponse<*>>(),
    Callback<ApiResponse<*>> {

    private val TAG = "ApiResponseLiveData"

    override fun onActive() {
        if (originCall.isCanceled || originCall.isExecuted) {
            return
        }
        originCall.enqueue(this)
    }

    override fun onInactive() {
        if (originCall.isCanceled) {
            return
        }
        originCall.cancel()
    }

    override fun onFailure(call: Call<ApiResponse<*>>, t: Throwable) {
        if (originCall.isCanceled) {
            return
        }
        postValue(
            ApiResponse<Nothing>(
                errorCode = -1,
                errorMsg = "网络请求错误"
            )
        )
        LOG.e(TAG, "onFailed ", t)
    }

    override fun onResponse(call: Call<ApiResponse<*>>, response: Response<ApiResponse<*>>) {
        val body = response.body()
        if (response.isSuccessful) {
            postValue(body)
        } else {
            if (body != null) {
                postValue(body)
                LOG.e(TAG, "onResponse isFailed code ${body.errorCode}, message ${body.errorMsg}")
            } else {
                postValue(
                    ApiResponse<Nothing>(
                        errorCode = response.code(),
                        errorMsg = response.message()
                    )
                )
                LOG.e(
                    TAG,
                    "onResponse isFailed code ${response.code()}, message ${response.message()}"
                )
            }
        }
    }
}