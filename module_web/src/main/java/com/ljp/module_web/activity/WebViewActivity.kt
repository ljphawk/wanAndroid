package com.ljp.module_web.activity

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.ljp.lib_base.extensions.gone
import com.ljp.lib_base.extensions.visible
import com.ljp.module_base.router.RouterPath
import com.ljp.module_base.ui.BaseBindingActivity
import com.ljp.module_web.OnPageChangedListener
import com.ljp.module_web.WebViewHelper
import com.ljp.module_web.WebViewManager
import com.ljp.module_web.databinding.ActivityWebViewBinding
import com.tencent.smtt.sdk.WebView
import com.therouter.router.Autowired
import com.therouter.router.Route


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 14:04.
 *@描述
 */

@Route(path = RouterPath.PATH_WEB, description = "WebViewActivity页面")
class WebViewActivity : BaseBindingActivity<ActivityWebViewBinding>() {

    @JvmField
    @Autowired
    var argsUrl: String? = ""

    private lateinit var webViewHelper: WebViewHelper

    override fun immersionBarView(): View {
        return binding.titleBar
    }

    override fun initData(savedInstanceState: Bundle?) {
        initWebView()
        initBackListener()
    }

    private fun initBackListener() {
        val onBackPressed = onBackPressedDispatcher.addCallback {
            if (!webViewHelper.canGoBack()) {
                binding.titleBar.leftView.performClick()
            }
        }
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                onBackPressed.isEnabled = false
                onBackPressed()
            }
        })
    }

    private fun initWebView() {
        val webView = WebViewManager.obtain(this)
        binding.flContainer.addView(
            webView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        )

        webViewHelper = WebViewHelper(webView).apply {
            setOnPageChangedListener(object : OnPageChangedListener {
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    binding.titleBar.title = title
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.visible()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.gone()

                }

                override fun onProgressChanged(view: WebView?, newProgress: Int) {
                    binding.progressBar.progress = newProgress
                }
            })
            loadUrl(Uri.decode(argsUrl))
        }
        lifecycle.addObserver(webViewHelper)
    }


}