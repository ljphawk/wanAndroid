package com.ljp.wanandroid.ui.fragment.articlelist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dylanc.viewbinding.getBinding
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.FragmentHotBinding
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.eventbus.AppEvent
import com.ljp.wanandroid.eventbus.CollectEvent
import com.ljp.wanandroid.model.ArticleBean
import com.ljp.wanandroid.model.ArticleListBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.preference.UserPreference
import com.ljp.wanandroid.ui.fragment.hot.binding
import com.ljp.wanandroid.ui.fragment.main.MainFragmentDirections
import com.ljp.wanandroid.ui.fragment.search.SearchFragmentDirections
import com.ljp.wanandroid.ui.fragment.search.SearchViewModel
import com.ljp.wanandroid.utils.LOG
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.respository.network.BaseApiResponse
import com.qszx.utils.extensions.getBundleParam
import com.qszx.utils.extensions.hasContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/*
 *@创建者       L_jp
 *@创建时间     2022/7/5 16:47.
 *@描述
 */
@AndroidEntryPoint
class ArticleListFragment : BaseBindingFragment<FragmentHotBinding>() {

    private val articleViewModel by viewModels<ArticleViewModel>()
    private val searchViewModel by viewModels<SearchViewModel>()

    private val _args by getBundleParam<ArticleListParams>(BUNDLE_ARGS)
    private val args: ArticleListParams
        get() {
            return _args!!
        }

    companion object {
        private const val BUNDLE_ARGS = "BUNDLE_ARGS"

        fun newInstance(listBundle: ArticleListParams): ArticleListFragment {
            val fragment = ArticleListFragment()
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_ARGS, listBundle)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        AppEvent.register(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.linear().setup {
            addType<MutableList<HomeBannerBean>>(R.layout.item_hot_article_head_view)
            addType<ArticleBean>(R.layout.item_hot_article_view)
            onBind {
                when (itemViewType) {
                    R.layout.item_hot_article_head_view -> {
                        getBinding<ItemHotArticleHeadViewBinding>().binding(
                            context,
                            viewLifecycleOwner,
                            getModel()
                        )
                    }

                    R.layout.item_hot_article_view -> {
                        getBinding<ItemHotArticleViewBinding>().binding(getModel())
                    }
                }
            }
            R.id.cl_article_item.onClick {
                articleItemClick(getModel())
            }
            R.id.tv_tag.onClick {

            }
            R.id.iv_collect.onClick {
                clickCollect(getModel())
            }
        }
        binding.pageRefreshLayout.onRefresh {
            getArticleData()
        }
        if (args.isAutoRefresh) {
            binding.pageRefreshLayout.showLoading()
        }
    }

    private fun articleItemClick(data: ArticleBean) {
        val directions = if (args.type is SearchArticleParams) {
            SearchFragmentDirections.actionSearchFragmentToWebViewFragment(data.link)
        } else {
            MainFragmentDirections.actionMainFragmentToWebViewFragment(data.link)
        }
        routerActivity?.navigate(directions)
    }

    private fun getPageIndex(): Int {
        return binding.pageRefreshLayout.index - if (args.startIndexIsZero) 1 else 0
    }

    private fun getArticleData() {
        val articleTypeParams = args.type
        val page = getPageIndex()
        when (articleTypeParams) {
            is HomeArticleParams -> {
                when (articleTypeParams.type) {//热门数据
                    HomeArticleType.HOT -> getHomeHotData()
                    HomeArticleType.SQUARE -> requestGetArticleListData {//广场数据
                        articleViewModel.getHomeSquareArticle(page)
                    }
                    HomeArticleType.QUESTION -> requestGetArticleListData {//问答列表
                        articleViewModel.getHomeQuestionArticle(page)
                    }
                }

            }
            is SearchArticleParams -> {
                requestGetArticleListData {//搜索
                    searchViewModel.search(page, articleTypeParams.content)
                }
            }
            else -> {
                throw  IllegalStateException("未处理的类型参数")
            }
        }
    }

    private fun clickCollect(model: ArticleBean) {
        if (UserPreference.isLogin()) {
            requestCollect(model)
        } else {
            routerActivity?.navigate(R.id.action_mainFragment_to_loginFragment)
        }
    }

    /**
     * 获取热门数据
     */
    private fun getHomeHotData() {
        if (binding.pageRefreshLayout.isRefreshing) {
            val flow1 = flow { emit(articleViewModel.getHomeBannerList()) }
            val flow2 = flow { emit(articleViewModel.getHomeTopArticle()) }
            val flow3 = flow { emit(articleViewModel.getHomeHotArticle(getPageIndex())) }
            combine(flow1, flow2, flow3) { a, b, c ->
                binding.recyclerView.bindingAdapter.clearHeader()
                binding.recyclerView.bindingAdapter.addHeader(a.data())
                binding.pageRefreshLayout.addData(articleZipData(b, c)?.datas) {
                    !(c.data()?.over ?: false)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope).start()
        } else {
            requestGetArticleListData { articleViewModel.getHomeHotArticle(getPageIndex()) }
        }
    }

    private fun articleZipData(
        data1: BaseApiResponse<MutableList<ArticleBean>>,
        data2: BaseApiResponse<ArticleListBean>,
    ): ArticleListBean? {
        if (data2.data()?.datas == null) {
            data2.data()?.datas = mutableListOf()
        }
        data2.data()?.datas?.addAll(0, data1.data() ?: mutableListOf())
        return data2.data()
    }

    /**
     * 文章列表
     */
    private fun requestGetArticleListData(
        showLoading: Boolean = false,
        complete: () -> Unit = {},
        function: suspend () -> BaseApiResponse<ArticleListBean>,
    ) {
        launchAndCollect(
            function,
            showLoading
        ) {
            onSuccess = {
                val isRefresh = binding.pageRefreshLayout.isRefreshing
                binding.pageRefreshLayout.addData(it?.datas) {
                    !(it?.over ?: false)
                }
                if (isRefresh && (it?.datas?.size ?: 0) >= 0) {//搜索场景下
                    binding.recyclerView.scrollToPosition(0)
                }
            }
            onFailed = { _, _ ->
                binding.pageRefreshLayout.addData(null)
            }
            onComplete = complete
        }
    }


    private fun requestCollect(model: ArticleBean) {
        launchAndCollect({ articleViewModel.collect(model.collect, model.id) }) {
            onSuccess = {
                AppEvent.post(CollectEvent(model.id, !model.collect))
            }
        }
    }

    fun searchKey(content: String) {
        if (args.type is SearchArticleParams) {
            (args.type as SearchArticleParams).content = content
            binding.pageRefreshLayout.autoRefresh()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventCollectArticle(event: CollectEvent) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val data = (binding.recyclerView.bindingAdapter.models as MutableList<*>?)
                if (data.hasContent()) {
                    data!!.forEachIndexed { index, any ->
                        val item = any as ArticleBean
                        if (event.articleId == item.id) {
                            item.collect = event.collect
                            val adapter = binding.recyclerView.bindingAdapter
                            adapter.notifyItemChanged(index + adapter.headerCount)
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onDestroyView() {
        AppEvent.unregister(this)
        super.onDestroyView()
    }

}