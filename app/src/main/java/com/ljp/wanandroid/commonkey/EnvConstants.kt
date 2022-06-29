package com.ljp.wanandroid.commonkey

import androidx.annotation.StringDef
import com.qszx.respository.BuildConfig
import com.ljp.wanandroid.preference.ConfigPreference


/*
 *@创建者       L_jp
 *@创建时间     2022/6/17 16:58.
 *@描述       环境常量信息
 */
@StringDef(value = [EnvStatus.RELEASE, EnvStatus.PREV, EnvStatus.DEBUG])
annotation class AppEnvStatus

object EnvStatus {
    const val RELEASE = "release"
    const val PREV = "prev"
    const val DEBUG = "debug"
}


object WebHostEnv {
    const val RELEASE = "https://zxzc.zx62580.com/zhixing/"
    const val PREV = "https://qaman.zx62580.com/zhixing/"
    const val DEBUG = "https://rent.zx62580.com:7343/zhixing/"
}

object ApiHostEnv {
    const val RELEASE = "https://zxzc.zx62580.com/zhixing/"
    const val PREV = "https://renttest.zx62580.com/zhixing/"
    const val DEBUG = "http://172.16.211.10:8000/"
}

object EnvConstant {

    fun getWebHost(): String {
        return if (!BuildConfig.DEBUG) {
            WebHostEnv.RELEASE
        } else {
            when (ConfigPreference.appHostStatus) {
                EnvStatus.RELEASE -> WebHostEnv.RELEASE
                EnvStatus.PREV -> WebHostEnv.PREV
                EnvStatus.DEBUG -> WebHostEnv.DEBUG
                else -> WebHostEnv.RELEASE
            }
        }
    }

    fun getApiHost(): String {
        return if (!BuildConfig.DEBUG) {
            ApiHostEnv.RELEASE
        } else {
            when (ConfigPreference.appHostStatus) {
                EnvStatus.RELEASE -> ApiHostEnv.RELEASE
                EnvStatus.PREV -> ApiHostEnv.PREV
                EnvStatus.DEBUG -> ApiHostEnv.DEBUG
                else -> ApiHostEnv.RELEASE
            }
        }
    }

}