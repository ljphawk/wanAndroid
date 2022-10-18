package com.ljp.wanandroid.test.fragment.tab1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ljp.wanandroid.databinding.FragmentTab1Binding
import com.qszx.base.ui.BaseBindingFragment


/*
 *@创建者       L_jp
 *@创建时间     2022/10/16 13:42.
 *@描述
 */
class TabFragment1 : BaseBindingFragment<FragmentTab1Binding>() {

    private val viewModel by viewModels<TabViewModel1>()

    override fun initData(view: View, savedInstanceState: Bundle?) {

    }
}