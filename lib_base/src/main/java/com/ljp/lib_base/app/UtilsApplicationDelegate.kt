package com.ljp.lib_base.app

import android.app.Application
import android.content.Context


/*
 *@创建者       L_jp
 *@创建时间     2022/10/23 18:49.
 *@描述
 */
class UtilsApplicationDelegate : ApplicationDelegate {

    companion object {
        lateinit var instance: Application
    }

    override fun attachBaseContext(application: Application, context: Context) {

    }

    override fun onCreate(application: Application) {
        instance = application
    }
}