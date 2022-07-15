package com.ljp.wanandroid.ui.fragment.hot

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.drake.brv.PageRefreshLayout
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dylanc.viewbinding.getBinding
import com.ljp.wanandroid.R
import com.ljp.wanandroid.constant.UrlConstant
import com.ljp.wanandroid.databinding.FragmentHotBinding
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.glide.loadImage
import com.ljp.wanandroid.model.HomeArticleBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.ui.fragment.home.HomeBannerAdapter
import com.ljp.wanandroid.ui.fragment.home.HomeViewModel
import com.ljp.wanandroid.utils.LOG
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.launch
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.respository.extensions.launchAndCollectIn
import com.qszx.respository.network.BaseApiResponse
import com.qszx.utils.showToast
import com.zhpan.bannerview.BannerViewPager
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.w3c.dom.Text


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
            addType<HomeArticleBean>(R.layout.item_hot_article_view)
            onBind {
                when (itemViewType) {
                    R.layout.item_hot_article_head_view -> {
                        getBinding<ItemHotArticleHeadViewBinding>().binding(context, viewLifecycleOwner, getModel())
                    }
                    R.layout.item_hot_article_view -> {
                        getBinding<ItemHotArticleViewBinding>().binding(getModel())
                    }
                }
            }
        }
        binding.pageRefreshLayout.onRefresh {
            getHomeInitData()
        }.refresh()
    }

    private fun initViewModelObserver() {
        homeViewModel.hotArticleState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            onSuccess = { data ->
                if (getPageRefreshLayoutPageIndex() == 0) {
                    binding.recyclerView.bindingAdapter.addModels(data?.datas)
                    binding.pageRefreshLayout.showContent(true)
                } else {
                    binding.pageRefreshLayout.addData(data?.datas) {
                        !(data?.over ?: false)
                    }
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
                binding.recyclerView.bindingAdapter.addModels(it, false, 0)
            }
        }
    }

    private fun getPageRefreshLayoutPageIndex(): Int {
        return binding.pageRefreshLayout.index - 1
    }

    private fun loadHomeHotArticle() {
        launch(false) { homeViewModel.getHomeHotArticle(getPageRefreshLayoutPageIndex()) }
    }

}