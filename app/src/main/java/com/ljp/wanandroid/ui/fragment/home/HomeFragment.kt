package com.ljp.wanandroid.ui.fragment.home

import android.os.Bundle
import android.view.View
import com.ljp.wanandroid.databinding.FragmentHomeBinding
import com.qszx.base.ui.BaseBindingFragment


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:01.
 *@描述
 */
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun initImmersionBar() {
//        super.initImmersionBar()
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {

    }
}