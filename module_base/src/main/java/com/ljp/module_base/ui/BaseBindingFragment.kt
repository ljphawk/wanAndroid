package com.ljp.module_base.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import com.gyf.immersionbar.ImmersionBar
import com.ljp.lib_base.callback.IUiView
import com.ljp.module_base.dialog.LoadingDialog


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 11:47 AM.
 *@描述
 */
abstract class BaseBindingFragment<VB : ViewBinding> : Fragment(), IUiView {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!
    private val loadingDialog by lazy { LoadingDialog.Builder(requireContext()) }

    protected lateinit var immersionBar: ImmersionBar
    var routerActivity: RouterActivity<*>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is RouterActivity<*>) {
            routerActivity = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ViewBindingUtil.inflateWithGeneric(this, inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initImmersionBar()
        initData(view, savedInstanceState)
    }

    protected abstract fun initData(view: View, savedInstanceState: Bundle?)

    protected open fun initImmersionBar() {
        //默认的沉浸式方式
        immersionBar = ImmersionBar.with(this)
            .transparentStatusBar()
            .navigationBarColor(android.R.color.white)
            .statusBarDarkFont(true, 0.2f)
            .navigationBarDarkIcon(true, 0.2f)
        val view = immersionBarView()
        when {
            view != null -> {
                immersionBar.fitsSystemWindows(false)
                    .titleBar(view)
                    .init()
            }
            fitsSystemWindowsStatusBarColor() > 0 -> {
                immersionBar.fitsSystemWindows(true)
                    .statusBarColor(fitsSystemWindowsStatusBarColor())
                    .init()
            }
        }

    }

    /**
     * 沉浸状态的viewId
     */
    protected open fun immersionBarView(): View? {
        return null
    }

    protected open fun fitsSystemWindowsStatusBarColor(): Int {
        return -1
    }

    override fun showLoading(loadingText: String) {
        if (!loadingDialog.isCreated()) {
            loadingDialog.create()
        }
        loadingDialog.setMessage(loadingText)
        if (!loadingDialog.isShowing()) {
            loadingDialog.show()
        }
    }

    override fun dismissLoading() {
        loadingDialog.dismiss()
    }

    fun finish(){
        routerActivity?.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity()?.let {
            com.ljp.lib_base.utils.CommUtils.hideSoftKeyBoard(it)
        }
        _binding = null
    }

}
