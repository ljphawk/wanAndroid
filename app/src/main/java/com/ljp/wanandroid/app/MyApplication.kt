package com.ljp.wanandroid.app

import android.os.Build
import androidx.core.content.ContextCompat
import com.drake.brv.PageRefreshLayout
import com.drake.statelayout.StateConfig
import com.facebook.stetho.Stetho
import com.ljp.wanandroid.R
import com.ljp.wanandroid.manager.WebViewManager
import com.qszx.respository.app.BaseApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebView
import dagger.hilt.android.HiltAndroidApp
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2022/6/9 10:38.
 *@描述
 */
@HiltAndroidApp
class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()

    }


}