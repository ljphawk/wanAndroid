package com.ljp.wanandroid.manager

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.*
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 17:24.
 *@描述
 */
class WebViewHelper constructor(private val webView: WebView) : DefaultLifecycleObserver {

    private var onPageChangedListener: OnPageChangedListener? = null

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        webView.onResume()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        webView.onPause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        WebViewManager.recycle(webView)
    }

    init {

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(p0: WebView?, p1: WebResourceRequest?): Boolean {
                return super.shouldOverrideUrlLoading(p0, p1)
            }
        }

        webView.setDownloadListener { url, _, _, _, _ ->
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                webView.context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        webView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
//                onPageChangedListener?.onProgressChanged(view, newProgress)
//                if (newProgress > 80 && injectVConsole && !injectState) {
//                    view?.apply { evaluateJavascript(context.injectVConsoleJs()) {} }
//                    injectState = true
//                }
            }
        }
    }
}


interface OnPageChangedListener {
    fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
    fun onPageFinished(view: WebView?, url: String?)
    fun onProgressChanged(view: WebView?, newProgress: Int)
}