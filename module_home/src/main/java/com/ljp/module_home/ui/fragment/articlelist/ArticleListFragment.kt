package com.ljp.module_home.ui.fragment.articlelist

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dylanc.viewbinding.getBinding
import com.ljp.module_base.network.data.ArticleBean
import com.ljp.module_base.network.data.ArticleListBean
import com.ljp.module_base.preference.UserPreference
import com.ljp.module_home.extensions.binding
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.repository.extensions.launchAndCollect
import com.ljp.repository.network.base.ApiResponse
import com.ljp.lib_base.extensions.getBundleParam
import com.ljp.lib_base.extensions.hasContent
import com.ljp.module_base.router.actiondata.CollectActionData
import com.ljp.module_base.extensions.theStartWebActivity
import com.ljp.module_base.network.data.HomeBannerBean
import com.ljp.module_base.router.RouterAction
import com.ljp.module_base.router.RouterPath
import com.ljp.module_home.R
import com.ljp.module_home.databinding.FragmentHotBinding
import com.ljp.module_home.databinding.ItemHotArticleHeadViewBinding
import com.ljp.module_home.databinding.ItemHotArticleViewBinding
import com.ljp.module_home.ui.fragment.search.SearchViewModel
import com.therouter.TheRouter
import com.therouter.router.action.interceptor.ActionInterceptor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


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
    private val collectActionInterceptor = object : ActionInterceptor() {
        override fun handle(context: Context, args: Bundle): Boolean {
            args.getParcelable<CollectActionData>("data")?.let {
                actionCollectArticle(it)
            }
            return false
        }
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
        initRecyclerView()
        TheRouter.addActionInterceptor(RouterAction.ACTION_COLLECT, collectActionInterceptor)
    }

    private fun initRecyclerView() {
        binding.recyclerView.linear().setup {
            addType<MutableList<HomeBannerBean>>(R.layout.item_hot_article_head_view)
            addType<ArticleBean>(R.layout.item_hot_article_view)
            onBind {
                when (itemViewType) {
                    R.layout.item_hot_article_head_view -> {
                        getBinding<ItemHotArticleHeadViewBinding>().binding(
                            viewLifecycleOwner,
                            getModel()
                        ) {
                            articleItemClick(it)
                        }
                    }

                    R.layout.item_hot_article_view -> {
                        getBinding<ItemHotArticleViewBinding>().binding(getModel())
                    }
                }
            }
            R.id.cl_article_item.onClick {
                articleItemClick(getModel<ArticleBean>().link)
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

    private fun articleItemClick(url: String?) {
        theStartWebActivity(RouterPath.PATH_WEB, url)
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
            //TODO
//            routerActivity?.navigate(R.id.action_mainFragment_to_loginFragment)
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
                binding.recyclerView.bindingAdapter.addHeader(a.data)
                binding.pageRefreshLayout.addData(articleZipData(b, c)?.datas) {
                    !(c.data?.over ?: false)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope).start()
        } else {
            requestGetArticleListData { articleViewModel.getHomeHotArticle(getPageIndex()) }
        }
    }

    private fun articleZipData(
        data1: ApiResponse<MutableList<ArticleBean>>,
        data2: ApiResponse<ArticleListBean>,
    ): ArticleListBean? {
        if (data2.data?.datas == null) {
            data2.data?.datas = mutableListOf()
        }
        data2.data?.datas?.addAll(0, data1.data ?: mutableListOf())
        return data2.data
    }

    /**
     * 文章列表
     */
    private fun requestGetArticleListData(
        showLoading: Boolean = false,
        complete: () -> Unit = {},
        function: suspend () -> ApiResponse<ArticleListBean>,
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
                TheRouter.build(RouterAction.ACTION_COLLECT)
                    .withParcelable("data", CollectActionData(model.id, !model.collect))
                    .navigation()
            }
        }
    }

    fun searchKey(content: String) {
        if (args.type is SearchArticleParams) {
            (args.type as SearchArticleParams).content = content
            binding.pageRefreshLayout.autoRefresh()
        }
    }

    private fun actionCollectArticle(event: CollectActionData) {
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
        super.onDestroyView()
        TheRouter.removeActionInterceptor(RouterAction.ACTION_COLLECT, collectActionInterceptor)
    }

}