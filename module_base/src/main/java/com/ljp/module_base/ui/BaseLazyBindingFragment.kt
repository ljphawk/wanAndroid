package com.ljp.module_base.ui

import android.os.Bundle
import android.view.View
import androidx.viewbinding.ViewBinding


/*
 *@创建者       L_jp
 *@创建时间     6/2/21 11:47 AM.
 *@描述
 */
abstract class BaseLazyBindingFragment<VB : ViewBinding> : BaseBindingFragment<VB>() {

    private var dataLoaded: Boolean = false

    override fun onResume() {
        super.onResume()
        lazyLoad()
    }

    private fun lazyLoad() {
        if (!dataLoaded) {
            dataLoaded = true
            loadData()
        }
    }


    override fun onDestroy() {
        dataLoaded = false
        super.onDestroy()
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {

    }

    /**
     * 加载数据
     */
    abstract fun loadData()
}
