package com.ljp.wanandroid.ui.fragment.hot

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.drake.brv.PageRefreshLayout
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dylanc.viewbinding.getBinding
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.FragmentHotBinding
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.model.HomeArticleBean
import com.ljp.wanandroid.model.HomeArticleListBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.ui.fragment.home.HomeViewModel
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.respository.network.BaseApiResponse
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn


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
        PageRefreshLayout.preloadIndex = 0
        binding.recyclerView.linear().setup {
            addType<MutableList<HomeBannerBean>>(R.layout.item_hot_article_head_view)
            addType<HomeArticleBean>(R.layout.item_hot_article_view)
            onBind {
                when (itemViewType) {
                    R.layout.item_hot_article_head_view -> {
                        getBinding<ItemHotArticleHeadViewBinding>().binding(context,
                            viewLifecycleOwner,
                            getModel())
                    }

                    R.layout.item_hot_article_view -> {
                        getBinding<ItemHotArticleViewBinding>().binding(getModel())
                    }
                }
            }
        }
        binding.pageRefreshLayout.onRefresh {
            getHomeData()
        }
        binding.pageRefreshLayout.showLoading()
    }

    private fun initViewModelObserver() {

    }

    private fun setBannerData(data: MutableList<HomeBannerBean>?) {
        binding.recyclerView.bindingAdapter.clearHeader()
        binding.recyclerView.bindingAdapter.addHeader(data)
    }


    private fun getHomeData() {
        val index = binding.pageRefreshLayout.index - 1
        if (binding.pageRefreshLayout.isRefreshing) {
            val flow1 = flow { emit(homeViewModel.getHomeBannerList()) }
            val flow2 = flow { emit(homeViewModel.getHomeTopArticle()) }
            val flow3 = flow { emit(homeViewModel.getHomeHotArticle(index)) }
            combine(flow1, flow2, flow3) { a, b, c ->
                setBannerData(a.data())
                binding.pageRefreshLayout.addData(articleZipData(b, c)?.datas) {
                    !(c.data()?.over ?: false)
                }
            }.launchIn(lifecycleScope).start()
        } else {
            launchAndCollect({ homeViewModel.getHomeHotArticle(index) },
                false) {
                onSuccess = {
                    binding.pageRefreshLayout.addData(it?.datas) {
                        !(it?.over ?: false)
                    }
                }
                onFailed = { _, _ ->
                    binding.pageRefreshLayout.addData(null)
                }
            }
        }

    }

    private fun articleZipData(
        data1: BaseApiResponse<MutableList<HomeArticleBean>>,
        data2: BaseApiResponse<HomeArticleListBean>,
    ): HomeArticleListBean? {
        if (data2.data()?.datas == null) {
            data2.data()?.datas = mutableListOf()
        }
        data2.data()?.datas?.addAll(0, data1.data() ?: mutableListOf())
        return data2.data()
    }

}