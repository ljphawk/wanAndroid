package com.ljp.respository.network.base

import android.annotation.SuppressLint
import com.ljp.lib_base.extensions.ThreadExecutorFactory
import com.ljp.respository.BuildConfig
import com.ljp.respository.network.livadata.LiveDataCallAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager


/*
 *@创建者       L_jp
 *@创建时间     2022/6/16 14:35.
 *@描述
 */
abstract class BaseRetrofitClient {


    private fun initDefaultClient(): OkHttpClient {
        val dispatcher = Dispatcher(ThreadExecutorFactory.getNetworkExecutor()).apply {
            maxRequests = 100
            maxRequestsPerHost = 64
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectionPool(ConnectionPool(10, 5, TimeUnit.MINUTES))
            .addInterceptor(loggingInterceptor)
            .dispatcher(dispatcher)

        val trustManager = IgnoreTrustManager()
        val sslSocketFactory = createSSLSocketFactory(trustManager)
        if (sslSocketFactory != null) {
            builder.sslSocketFactory(
                sslSocketFactory,
                trustManager
            )
            builder.hostnameVerifier { _, _ -> true }
        }

        //设置禁止抓包
//    if (!BuildConfig.DEBUG) {
//        builder.proxy(Proxy.NO_PROXY)
//    }
        handleBuilder(builder)
        return builder.build()
    }


    private fun createSSLSocketFactory(trustManager: IgnoreTrustManager): SSLSocketFactory? {
        return try {
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, arrayOf(trustManager), SecureRandom())
            sc.socketFactory
        } catch (e: Exception) {
            null
        }
    }

    abstract fun handleBuilder(builder: OkHttpClient.Builder)

    abstract fun baseUrl(): String


    fun <Service> getApiService(serviceClass: Class<Service>): Service {
        return getApiService(serviceClass, baseUrl())
    }

    fun <Service> getApiService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(initDefaultClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }

}

@SuppressLint("CustomX509TrustManager")
class IgnoreTrustManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {

    }

    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {

    }

    override fun getAcceptedIssuers(): Array<X509Certificate> {
        return arrayOf()
    }
}