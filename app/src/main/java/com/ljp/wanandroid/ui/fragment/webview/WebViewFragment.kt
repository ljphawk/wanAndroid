package com.ljp.wanandroid.ui.fragment.webview

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.ljp.wanandroid.databinding.FragmentWebViewBinding
import com.ljp.wanandroid.manager.WebViewHelper
import com.ljp.wanandroid.manager.WebViewManager
import com.qszx.base.ui.BaseBindingFragment
import com.tencent.smtt.sdk.WebView


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 14:04.
 *@描述
 */
class WebViewFragment : BaseBindingFragment<FragmentWebViewBinding>() {

    private val args: WebViewFragmentArgs by navArgs()
    private lateinit var webView: WebView
    private lateinit var webViewHelper: WebViewHelper

    override fun immersionBarView(): View {
        return binding.titleBar
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {

        initWebView()
    }

    private fun initWebView() {
        webView = WebViewManager.obtain(requireActivity())
        webViewHelper = WebViewHelper(webView)
        viewLifecycleOwner.lifecycle.addObserver(webViewHelper)

        binding.flContainer.addView(
            webView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        webView.loadUrl(args.url)
    }
}