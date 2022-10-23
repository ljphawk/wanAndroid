package com.ljp.wanandroid.manager

import android.app.Application
import android.os.Build
import androidx.core.content.ContextCompat
import com.drake.brv.PageRefreshLayout
import com.drake.statelayout.StateConfig
import com.facebook.stetho.Stetho
import com.ljp.wanandroid.R
import com.qszx.base.app.BaseApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebView
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 17:12.
 *@描述
 */
object SdkInitManager {

    fun init() {
        initX5WebView()

        initSmartRefresh()
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

    private fun initSmartRefresh() {
        PageRefreshLayout.preloadIndex = 0
        StateConfig.apply {
            emptyLayout = R.layout.layout_empty
            errorLayout = R.layout.layout_error
            loadingLayout = R.layout.layout_loading

            setRetryIds(R.id.msg)

            onLoading {
                // 此生命周期可以拿到LoadingLayout创建的视图对象, 可以进行动画设置或点击事件.
            }
        }

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setPrimaryColorsId(R.color.transparent) //全局设置主题颜色
            MaterialHeader(context)
        }
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ -> //指定为经典Footer，默认是 BallPulseFooter
            val footer = ClassicsFooter(context)
            footer.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent))
            footer
        }
    }
}