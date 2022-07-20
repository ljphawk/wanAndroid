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
import com.ljp.wanandroid.model.ArticleBean
import com.ljp.wanandroid.model.ArticleListBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.preference.UserPreference
import com.ljp.wanandroid.ui.fragment.hot.binding
import com.ljp.wanandroid.ui.fragment.main.MainFragmentDirections
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.respository.network.BaseApiResponse
import com.qszx.utils.extensions.getBundleParam
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
class ArticleListFragment : BaseBindingFragment<FragmentHotBinding>() {

    private val articleViewModel by viewModels<ArticleViewModel>()

    private val articleTypeParams by getBundleParam<ArticleListClassify>(BUNDLE_TYPE)
    private val startIndexIsZero by getBundleParam<Boolean>(BUNDLE_START_INDEX, true)

    companion object {
        private const val BUNDLE_TYPE = "BUNDLE_TYPE"
        private const val BUNDLE_START_INDEX = "BUNDLE_START_INDEX"

        fun newInstance(listBundle: ArticleListParams): ArticleListFragment {
            val fragment = ArticleListFragment()
            val bundle = Bundle()
            bundle.putParcelable(BUNDLE_TYPE, listBundle.type)
            bundle.putBoolean(BUNDLE_START_INDEX, listBundle.startIndexIsZero)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
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
                val data = getModel<ArticleBean>()
                routerActivity?.navigate(MainFragmentDirections.actionMainFragmentToWebViewFragment(data.link))
            }
            R.id.tv_tag.onClick {

            }
            R.id.iv_collect.onClick {
                clickCollect(getModel(), layoutPosition)
            }
        }
        binding.pageRefreshLayout.onRefresh {
            getArticleData()
        }
        binding.pageRefreshLayout.showLoading()
    }

    private fun getPageIndex(): Int {
        return binding.pageRefreshLayout.index - if (startIndexIsZero!!) 1 else 0
    }

    private fun getArticleData() {
        if (articleTypeParams == null) {
            throw  IllegalStateException("未处理的类型参数")
        }
        if (articleTypeParams is HomeArticleParams) {
            when ((articleTypeParams as HomeArticleParams).type) {
                HomeArticleType.HOT -> getHomeHotData()
                HomeArticleType.SQUARE -> getSquareData()
                HomeArticleType.QUESTION -> getHomeQuestionData()
            }

        }
    }

    private fun clickCollect(model: ArticleBean, layoutPosition: Int) {
        if (UserPreference.isLogin()) {
            requestCollect(model, layoutPosition)
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
            launchAndCollect(
                { articleViewModel.getHomeHotArticle(getPageIndex()) },
                false
            ) {
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

    /**
     * 获取广场数据
     */
    private fun getSquareData() {
        launchAndCollect(
            { articleViewModel.getHomeSquareArticle(getPageIndex()) },
            false
        ) {
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

    /**
     * 问答列表
     */
    private fun getHomeQuestionData() {
        launchAndCollect(
            { articleViewModel.getHomeQuestionArticle(getPageIndex()) },
            false
        ) {
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

    private fun requestCollect(model: ArticleBean, layoutPosition: Int) {
        launchAndCollect({ articleViewModel.collect(model.collect, model.id) }) {
            onSuccess = {
                model.collect = !model.collect
                binding.recyclerView.bindingAdapter.notifyItemChanged(layoutPosition)
            }
        }
    }


}