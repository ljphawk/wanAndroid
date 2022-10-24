package com.ljp.wanandroid.ui.fragment.webview

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.hjq.bar.OnTitleBarListener
import com.hjq.bar.TitleBar
import com.ljp.wanandroid.databinding.FragmentWebViewBinding
import com.ljp.wanandroid.manager.WebViewManager
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.lib_base.extensions.getBundleParam
import com.ljp.lib_base.extensions.gone
import com.ljp.lib_base.extensions.visible
import com.tencent.smtt.sdk.WebView


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 14:04.
 *@描述
 */
class WebFragment : BaseBindingFragment<FragmentWebViewBinding>() {
    companion object {
        const val KEY_URL = "url"
    }

    private val argsUrl by getBundleParam(KEY_URL, "")
    private lateinit var webViewHelper: WebViewHelper

    override fun immersionBarView(): View {
        return binding.titleBar
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initWebView()
        initBackListener()


    }

    private fun initBackListener() {
        val onBackPressed = requireActivity().onBackPressedDispatcher.addCallback {
            if (!webViewHelper.canGoBack()) {
                binding.titleBar.leftView.performClick()
            }
        }
        binding.titleBar.setOnTitleBarListener(object : OnTitleBarListener {
            override fun onLeftClick(titleBar: TitleBar?) {
                onBackPressed.isEnabled = false
                requireActivity().onBackPressed()
            }
        })
    }

    private fun initWebView() {
        val webView = WebViewManager.obtain(requireActivity())
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
        viewLifecycleOwner.lifecycle.addObserver(webViewHelper)


    }


}