package com.qszx.respository.app

import android.app.Application
import com.tencent.mmkv.MMKV


/*
 *@创建者       L_jp
 *@创建时间     2020/7/19 10:44.
 *@描述
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        MMKV.initialize(this)
    }

}