package com.ljp.wanandroid.ui.fragment.home

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import com.example.lib_imageload.glide.loadImageCircleCrop
import com.ljp.wanandroid.constant.Router
import com.ljp.wanandroid.databinding.FragmentHomeBinding
import com.ljp.repository.data.SearchHotKeyBean
import com.ljp.module_base.preference.ConfigPreference
import com.ljp.module_base.preference.UserPreference
import com.ljp.wanandroid.ui.fragment.main.MainFragment
import com.ljp.wanandroid.ui.fragment.search.SearchFragment
import com.ljp.wanandroid.ui.fragment.search.SearchViewModel
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.repository.extensions.launchAndCollect
import com.ljp.repository.extensions.parseList
import com.ljp.repository.extensions.toJsonString
import com.ljp.lib_base.extensions.noQuickClick
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
            (parentFragment as MainFragment).binding.drawerLayout.apply {
                if (isDrawerOpen(GravityCompat.START)) {
                    closeDrawer(GravityCompat.START)
                } else {
                    openDrawer(GravityCompat.START)
                }
            }
        }
        binding.marqueeView.setOnClickListener {
            routerActivity?.navigate(Router.SEARCH)
        }
        binding.marqueeView.setOnItemClickListener { _, textView ->
            routerActivity?.navigate(Router.SEARCH,
                bundleOf(SearchFragment.KEY_SEARCH to textView.text.toString()))
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