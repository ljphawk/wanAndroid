package com.ljp.module_web.app

import android.app.Application
import android.content.Context
import android.os.Build
import com.ljp.lib_base.delegate.ApplicationDelegate
import com.ljp.module_base.app.BaseApplication
import com.ljp.module_web.WebViewManager
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebView


/*
 *@创建者       L_jp
 *@创建时间     2022/10/23 18:49.
 *@描述
 */
class WebApplicationDelegate : ApplicationDelegate {

    companion object {
        lateinit var instance: Application
    }

    override fun attachBaseContext(application: Application, context: Context) {

    }

    override fun onCreate(application: Application) {
        instance = application
        initX5WebView()
    }


    private fun initX5WebView() {
        //https://developer.android.com/about/versions/pie/android-9.0-changes-28?hl=zh-cn
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = Application.getProcessName()
            if (BaseApplication.instance.packageName != processName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        QbSdk.setDownloadWithoutWifi(true)
        QbSdk.initX5Environment(BaseApplication.instance, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                // 内核初始化完成，可能为系统内核，也可能为系统内核
            }

            /**
             * 预初始化结束
             * 由于X5内核体积较大，需要依赖网络动态下发，所以当内核不存在的时候，默认会回调false，此时将会使用系统内核代替
             * @param isX5 是否使用X5内核
             */
            override fun onViewInitFinished(isX5: Boolean) {
            }
        })
        WebViewManager.prepare(BaseApplication.instance)
    }

}