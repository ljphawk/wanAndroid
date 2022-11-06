package com.ljp.module_home.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lib_imageload.glide.loadImageCircleCrop
import com.ljp.lib_base.extensions.noQuickClick
import com.ljp.lib_base.utils.LOG
import com.ljp.module_base.preference.ConfigPreference
import com.ljp.module_base.preference.UserPreference
import com.ljp.module_base.router.RouterAction
import com.ljp.module_base.router.RouterPath
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.module_home.databinding.FragmentHomeBinding
import com.ljp.module_home.ui.fragment.search.SearchViewModel
import com.ljp.repository.data.SearchHotKeyBean
import com.ljp.repository.extensions.launchAndCollect
import com.ljp.repository.extensions.parseList
import com.ljp.repository.extensions.toJsonString
import com.therouter.TheRouter
import dagger.hilt.android.AndroidEntryPoint


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:01.
 *@描述
 */
@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

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
        getSearchHotKey()
        binding.ivAvatar.loadImageCircleCrop(UserPreference.getUserAvatarUrl())
    }

    private fun initViewListener() {
        initTabLayoutAndViewPage2()
        binding.ivAvatar.noQuickClick {
            TheRouter.build(RouterAction.ACTION_SWITCH_DRAWER_LAYOUT).navigation()
        }
        binding.marqueeView.setOnClickListener {
            TheRouter.build(RouterPath.PATH_SEARCH).navigation()

        }
        binding.marqueeView.setOnItemClickListener { _, textView ->
            TheRouter.build(RouterPath.PATH_SEARCH)
                .withString("argsSearch", textView.text.toString())
                .navigation()
        }
    }

    private fun initTabLayoutAndViewPage2() {
        binding.viewpager2.adapter =
            HomeAdapter(homeViewModel.getAdapterFragmentList(),
                childFragmentManager,
                viewLifecycleOwner.lifecycle)
        binding.tabLayout.setViewPager2(binding.viewpager2)
        binding.tabLayout.setTabData(homeViewModel.getTabLayoutData())
    }


    private fun getSearchHotKey() {
        launchAndCollect({ searchViewModel.searchHotKeyList() },
            showLoading = false,
            showErrorToast = false) {
            onSuccess = {
                ConfigPreference.searchHotKey = toJsonString(it)
            }
            onComplete = {
                setSearchHotKeyAdapter()
            }
        }
    }

    private fun setSearchHotKeyAdapter() {
        val data = parseList(ConfigPreference.searchHotKey, SearchHotKeyBean::class.java).map {
            it.name
        }
        binding.marqueeView.startWithList(data)
    }

    override fun onStart() {
        super.onStart()
        binding.marqueeView.startFlipping()
    }

    override fun onStop() {
        super.onStop()
        binding.marqueeView.stopFlipping()
    }
}