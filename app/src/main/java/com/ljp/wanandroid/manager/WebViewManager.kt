package com.ljp.wanandroid.manager

import android.content.Context
import android.content.MutableContextWrapper
import android.graphics.Color
import android.os.Looper
import android.view.ViewGroup
import com.tencent.smtt.export.external.interfaces.IX5WebSettings
import com.tencent.smtt.sdk.CookieManager
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 17:04.
 *@描述
 */
class WebViewManager {

    companion object {
        @Volatile
        private var INSTANCE: WebViewManager? = null

        private fun instance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: WebViewManager().also { INSTANCE = it }
        }

        fun prepare(context: Context) {
            instance().prepare(context)
        }

        fun obtain(context: Context): WebView {
            return instance().obtain(context)
        }

        fun recycle(webView: WebView) {
            instance().recycle(webView)
        }

        fun destroy() {
            instance().destroy()
        }
    }

    private val webViewCache: MutableList<WebView> = ArrayList(1)

    private fun create(context: Context): WebView {
        val webView = WebView(context)
        webView.setBackgroundColor(Color.TRANSPARENT)
        webView.view.setBackgroundColor(Color.TRANSPARENT)
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        webView.view.overScrollMode = WebView.OVER_SCROLL_NEVER
        val webSetting = webView.settings
        webSetting.javaScriptCanOpenWindowsAutomatically = true
        webSetting.allowFileAccess = true
        webSetting.setAppCacheEnabled(true)
        webSetting.cacheMode = WebSettings.LOAD_DEFAULT
        webSetting.domStorageEnabled = true
        webSetting.setGeolocationEnabled(true)
        webSetting.javaScriptEnabled = true
        webSetting.loadWithOverviewMode = true
        webSetting.databaseEnabled = true
        webSetting.setSupportZoom(true)
        webSetting.builtInZoomControls = true
        webSetting.setSupportMultipleWindows(true)
        webSetting.displayZoomControls = false
        webSetting.useWideViewPort = true
        webSetting.mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)
        webView.settingsExtension?.apply {
            setContentCacheEnable(true)
            setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY)
        }
        return webView
    }

    /**
     * 预创建
     */
    fun prepare(context: Context) {
        if (webViewCache.isEmpty()) {
            Looper.myQueue().addIdleHandler {
                if (webViewCache.isEmpty()) {
                    webViewCache.add(create(MutableContextWrapper(context)))
                }
                false
            }
        }
    }


    fun obtain(context: Context): WebView {
        if (webViewCache.isEmpty()) {
            webViewCache.add(create(MutableContextWrapper(context)))
        }
        val webView = webViewCache.removeFirst()
        val contextWrapper = webView.context as MutableContextWrapper
        contextWrapper.baseContext = context
        webView.clearHistory()
        webView.resumeTimers()
        return webView
    }


    fun recycle(webView: WebView) {
        try {
            webView.stopLoading()
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
            webView.clearHistory()
            webView.pauseTimers()
            webView.webChromeClient = null
            webView.webViewClient = null
            val parent = webView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(webView)
            }
            val contextWrapper = webView.context as MutableContextWrapper
            contextWrapper.baseContext = webView.context.applicationContext
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (!webViewCache.contains(webView)) {
                webViewCache.add(webView)
            }
        }
    }

    fun destroy() {
        try {
            webViewCache.forEach {
                it.removeAllViews()
                it.destroy()
                webViewCache.remove(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}