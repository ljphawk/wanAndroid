package com.qszx.base.app

import android.app.Application
import android.content.Context


/*
 *@创建者       L_jp
 *@创建时间     2022/10/23 18:46.
 *@描述
 */
public interface ApplicationDelegate {
    fun attachBaseContext(application: Application, context: Context)
    fun onCreate(application: Application)
}
