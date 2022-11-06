package com.ljp.module_navigation.ui.fragment

import com.ljp.module_base.ui.BaseLazyBindingFragment
import com.ljp.module_navigation.databinding.FragmentNavigationBinding


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:51.
 *@描述
 */
class NavigationFragment : BaseLazyBindingFragment<FragmentNavigationBinding>() {

    companion object {
        fun newInstance(): NavigationFragment {
            return NavigationFragment()
        }
    }

    override fun loadData() {

    }
}