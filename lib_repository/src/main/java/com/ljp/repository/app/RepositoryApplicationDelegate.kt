package com.ljp.repository.app

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.ljp.lib_base.delegate.ApplicationDelegate
import com.tencent.mmkv.MMKV
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2022/10/23 18:49.
 *@描述
 */
class RepositoryApplicationDelegate : ApplicationDelegate {

    companion object {
        lateinit var instance: Application
    }

    override fun attachBaseContext(application: Application, context: Context) {

    }

    override fun onCreate(application: Application) {
        instance = application
        MMKV.initialize(application)
        LitePal.initialize(application)
        Stetho.initializeWithDefaults(application)
    }
}