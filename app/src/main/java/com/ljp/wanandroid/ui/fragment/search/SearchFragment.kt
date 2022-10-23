package com.ljp.wanandroid.ui.fragment.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.FragmentSearchBinding
import com.ljp.wanandroid.databinding.ItemSearchHotKeyBinding
import com.qszx.respository.dao.SearchHistoryDao
import com.ljp.wanandroid.extensions.addTextChangedListener
import com.ljp.wanandroid.extensions.setTextAndSelection
import com.qszx.respository.data.SearchHotKeyBean
import com.qszx.respository.preference.ConfigPreference
import com.ljp.wanandroid.ui.fragment.articlelist.ArticleListFragment
import com.ljp.wanandroid.ui.fragment.articlelist.ArticleListParams
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.parseList
import com.qszx.utils.CommUtils
import com.qszx.utils.extensions.*
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 13:19.
 *@描述
 */
@AndroidEntryPoint
class SearchFragment : BaseBindingFragment<FragmentSearchBinding>() {

    companion object {
        const val KEY_SEARCH = "content"
    }

    private val argsSearch by getBundleParam(KEY_SEARCH, "")

    private val articleListFragment by lazy {
        ArticleListFragment.newInstance(ArticleListParams.getSearchParams())
    }

    override fun immersionBarView(): View {
        return binding.clSearch
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initViewListener()
        initSearchHotKey()
        initSearchHistory()
        initSearchEdittext()
        childFragmentManager.beginTransaction().add(R.id.fl_container, articleListFragment).commit()
    }

    private fun initViewListener() {
        binding.tvCancel.noQuickClick {
            routerActivity?.popBackStack()
        }
        binding.ivClearContent.setOnClickListener {
            binding.etSearch.text?.clear()
            showSearchContent(false)
        }
        binding.tvClearHistory.noQuickClick {
            //TODO 二次确认
            SearchHistoryDao.clearSearchHistory()
            initSearchHistory()
        }
    }

    private fun initSearchEdittext() {
        if (binding.ivClearContent.show(argsSearch.contentHasValue())) {
            binding.etSearch.setTextAndSelection(argsSearch!!)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            delay(300)
            binding.etSearch.requestFocus()
            CommUtils.showInputManager(requireContext(), binding.etSearch)
        }
        binding.etSearch.addTextChangedListener {
            binding.ivClearContent.show(it.contentHasValue())
        }
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                search(binding.etSearch.getContent())
            }
            true
        }
    }

    private fun initSearchHotKey() {
        lifecycleScope.launch {
            val hotKeyData = withContext(Dispatchers.IO) {
                parseList(ConfigPreference.searchHotKey,
                    SearchHotKeyBean::class.java).filter { it.name.contentHasValue() }.map {
                    it.name!!
                }
            }
            initFlowlayout(binding.hotKeyFlowlayout, hotKeyData)
        }
    }

    private fun initSearchHistory() {
        lifecycleScope.launch {
            val historyData = withContext(Dispatchers.IO) { SearchHistoryDao.getSearchHistory() }
            if (binding.llSearchHistory.show(historyData.hasContent())) {
                initFlowlayout(binding.historyFlowlayout, historyData)
            }
        }
    }

    private fun initFlowlayout(flowLayout: TagFlowLayout, list: List<String>) {
        var lastRemoveView: ImageView? = null
        flowLayout.adapter = object : TagAdapter<String>(list) {
            override fun getView(
                parent: FlowLayout?,
                position: Int,
                data: String,
            ): View {
                val itemBinding = ItemSearchHotKeyBinding.inflate(layoutInflater, parent, false)

                itemBinding.tvItem.text = data
                itemBinding.ivRemove.gone()

                itemBinding.root.setOnLongClickListener {
                    if (flowLayout == binding.historyFlowlayout) {
                        itemBinding.ivRemove.visible()
                        lastRemoveView?.gone()
                        lastRemoveView = itemBinding.ivRemove
                    }
                    flowLayout == binding.historyFlowlayout
                }
                if (flowLayout == binding.historyFlowlayout) {
                    itemBinding.ivRemove.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            SearchHistoryDao.removeSearchHistory(data)
                        }
                        initSearchHistory()
                    }
                }
                itemBinding.root.noQuickClick {
                    search(data)
                }
                return itemBinding.root
            }
        }
    }

    private fun saveSearchHotKey(data: SearchHotKeyBean) {
        lifecycleScope.launch {
            val save = async {
                SearchHistoryDao.saveSearchHistory(data)
            }
            if (save.await()) {
                initSearchHistory()
            }
        }
    }

    private fun search(searchKey: String) {
        if (searchKey.isEmpty()) {
            return
        }
        CommUtils.hideSoftKeyBoard(binding.etSearch)
        binding.etSearch.setTextAndSelection(searchKey)
        showSearchContent(true)
        saveSearchHotKey(SearchHotKeyBean(searchKey))
        articleListFragment.searchKey(searchKey)
    }

    private fun showSearchContent(show: Boolean) {
        binding.flContainer.show(show)
        binding.llSearchClassify.show(!show)
    }

}