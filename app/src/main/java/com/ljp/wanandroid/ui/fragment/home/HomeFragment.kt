package com.ljp.wanandroid.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.ljp.wanandroid.databinding.FragmentHomeBinding
import com.ljp.wanandroid.glide.loadImage
import com.ljp.wanandroid.glide.loadImageCircleCrop
import com.ljp.wanandroid.preference.UserPreference
import com.ljp.wanandroid.ui.fragment.main.MainFragment
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.utils.extensions.noQuickClick
import dagger.hilt.android.AndroidEntryPoint


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:01.
 *@描述
 */
@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    private val homeViewModel by viewModels<HomeViewModel>()

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

    override fun immersionBarView(): View {
        return binding.appBarLayout
    }


    override fun initData(view: View, savedInstanceState: Bundle?) {
        initViewListener()
        binding.ivAvatar.loadImageCircleCrop(UserPreference.getUserAvatarUrl())
    }

    private fun initViewListener() {
        initTabLayoutAndViewPage2()
        binding.ivAvatar.noQuickClick {
            (parentFragment as MainFragment).binding.drawerLayout.apply {
                if (isDrawerOpen(GravityCompat.START)) {
                    closeDrawer(GravityCompat.START)
                } else {
                    openDrawer(GravityCompat.START)
                }
            }
        }
    }

    private fun initTabLayoutAndViewPage2() {
        binding.viewpager2.adapter =
            HomeAdapter(homeViewModel.getAdapterFragmentList(), childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.tabLayout.setViewPager2(binding.viewpager2)
        binding.tabLayout.setTabData(homeViewModel.getTabLayoutData())
    }


}