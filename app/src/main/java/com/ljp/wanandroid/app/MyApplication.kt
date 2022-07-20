package com.ljp.wanandroid.app

import android.os.Build
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.drake.brv.PageRefreshLayout
import com.drake.statelayout.StateConfig
import com.facebook.stetho.Stetho
import com.ljp.wanandroid.R
import com.qszx.respository.app.BaseApplication
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
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
        webViewSetPath()

        LitePal.initialize(this)
        Stetho.initializeWithDefaults(this)
        initSmartRefresh()
    }

    /**
     * 28+的webView行为变更
     */
    private fun webViewSetPath() {
        //https://developer.android.com/about/versions/pie/android-9.0-changes-28?hl=zh-cn
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val processName = getProcessName()
            if (packageName != processName) {
                WebView.setDataDirectorySuffix(processName)
            }
        }
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