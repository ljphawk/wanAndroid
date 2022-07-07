package com.ljp.wanandroid.ui.fragment.hot

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dylanc.viewbinding.getBinding
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.FragmentHotBinding
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.model.HomeArticleBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.ui.fragment.home.HomeViewModel
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.launch
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.respository.extensions.launchAndCollectIn
import dagger.hilt.android.AndroidEntryPoint


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 16:47.
 *@描述
 */
@AndroidEntryPoint
class HotFragment : BaseBindingFragment<FragmentHotBinding>() {


    private val homeViewModel by viewModels<HomeViewModel>()


    companion object {
        fun newInstance(): HotFragment {
            return HotFragment()
        }
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        initViewModelObserver()
    }

    private fun initRecyclerView() {
        binding.pageRefreshLayout.onRefresh {
            getHomeInitData()
        }.refresh()
        binding.recyclerView.linear().setup {
            addType<MutableList<HomeBannerBean>>(R.layout.item_hot_article_head_view)
            addType<HomeArticleBean>(R.layout.item_hot_article_view)
        }.onBind {
            when (itemViewType) {
                R.layout.item_hot_article_head_view -> {
                    getBinding<ItemHotArticleHeadViewBinding>().binding(viewLifecycleOwner.lifecycle, getModel())
                }
                R.layout.item_hot_article_view -> {
                    getBinding<ItemHotArticleViewBinding>().binding(getModel())
                }
            }
        }
    }

    private fun initViewModelObserver() {
        homeViewModel.hotArticleState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            onSuccess = { data ->
                binding.pageRefreshLayout.addData(data?.datas) {
                    !(data?.over ?: false)
                }
            }
            onFailed = { _, _ ->
                binding.pageRefreshLayout.addData(null)
            }
        }
    }

    private fun getHomeInitData() {
        if (binding.pageRefreshLayout.isRefreshing) {
            getHomeBannerData()
            getHomeTopArticleData()
        }
        loadHomeHotArticle()
    }

    private fun getHomeBannerData() {
        launchAndCollect({ homeViewModel.getHomeBannerList() }, false) {
            onSuccess = {
                setBannerData(it)
            }
            onFailed = { _, _ ->
                setBannerData(null)
            }
        }
    }

    private fun setBannerData(data: MutableList<HomeBannerBean>?) {
        binding.recyclerView.bindingAdapter.clearHeader()
        binding.recyclerView.bindingAdapter.addHeader(data)
    }


    private fun getHomeTopArticleData() {
        launchAndCollect({ homeViewModel.getHomeTopArticle() }, false) {
            onSuccess = {
//                hotArticleAdapter.addData(0, it ?: mutableListOf())
            }
            onFailed = { _, _ ->

            }
        }
    }

    private fun loadHomeHotArticle() {
        launch(false) { homeViewModel.getHomeHotArticle(binding.pageRefreshLayout.index - 1) }
    }


}