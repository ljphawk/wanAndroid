package com.ljp.module_base.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil
import com.gyf.immersionbar.ImmersionBar
import com.ljp.lib_base.callback.IUiView
import com.ljp.module_base.dialog.LoadingDialog
import com.ljp.module_base.manager.ActivityManager

/*
 *@创建者       L_jp
 *@创建时间     2020/5/4 16:19.
 *@描述
 *
 *@更新者         $
 *@更新时间         $
 *@更新描述
 */
abstract class BaseBindingActivity<VB : ViewBinding> : AppCompatActivity(), IUiView {


    private val loadingDialog by lazy { LoadingDialog.Builder(this) }

    protected lateinit var immersionBar: ImmersionBar

    /**
     * 子类使用tag
     */
    protected var TAG: String = this::class.java.simpleName

    lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityManager.addActivity(this)

        binding = ViewBindingUtil.inflateWithGeneric(this, layoutInflater)
        setContentView(binding.root)
        initImmersionBar()
        //数据初始化
        initData(savedInstanceState)
    }

    /**
     *初始化数据,设置数据
     */
    protected abstract fun initData(savedInstanceState: Bundle?)


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
            }
            fitsSystemWindowsStatusBarColor() > 0 -> {
                immersionBar.fitsSystemWindows(true)
                    .statusBarColor(fitsSystemWindowsStatusBarColor())
            }
        }
        immersionBar.init()
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

    override fun onDestroy() {
        dismissLoading()
        ActivityManager.removeActivity(this)
        super.onDestroy()
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


}