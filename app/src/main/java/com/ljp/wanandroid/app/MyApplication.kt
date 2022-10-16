package com.ljp.wanandroid.app

import com.qszx.base.BaseApplication
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp


/*
 *@创建者       L_jp
 *@创建时间     2022/6/9 10:38.
 *@描述
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }


}