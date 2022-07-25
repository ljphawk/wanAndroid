package com.ljp.wanandroid.ui.fragment.search

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.FragmentSearchBinding
import com.ljp.wanandroid.databinding.ItemSearchHotKeyBinding
import com.ljp.wanandroid.db.SearchDb
import com.ljp.wanandroid.extensions.addTextChangedListener
import com.ljp.wanandroid.extensions.setTextAndSelection
import com.ljp.wanandroid.model.SearchHotKeyBean
import com.ljp.wanandroid.preference.ConfigPreference
import com.ljp.wanandroid.ui.fragment.articlelist.ArticleListFragment
import com.ljp.wanandroid.ui.fragment.articlelist.ArticleListParams
import com.ljp.wanandroid.utils.LOG
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.parseList
import com.qszx.utils.CommUtils
import com.qszx.utils.extensions.*
import com.qszx.utils.showToast
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

    private val args by navArgs<SearchFragmentArgs>()
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
            SearchDb.clearSearchHistory()
            initSearchHistory()
        }
    }

    private fun initSearchEdittext() {
        val content = args.content ?: ""
        if (binding.ivClearContent.show(content.contentHasValue())) {
            binding.etSearch.setTextAndSelection(content)
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
                parseList(
                    ConfigPreference.searchHotKey,
                    SearchHotKeyBean::class.java
                )
            }
            initFlowlayout(binding.hotKeyFlowlayout, hotKeyData)
        }
    }

    private fun initSearchHistory() {
        lifecycleScope.launch {
            val historyData = withContext(Dispatchers.IO) { SearchDb.getSearchHistory() }
            if (binding.llSearchHistory.show(historyData.hasContent())) {
                initFlowlayout(binding.historyFlowlayout, historyData)
            }
        }
    }

    private fun initFlowlayout(flowLayout: TagFlowLayout, list: List<SearchHotKeyBean>) {
        var lastRemoveView: ImageView? = null
        flowLayout.adapter = object : TagAdapter<SearchHotKeyBean>(list) {
            override fun getView(
                parent: FlowLayout?,
                position: Int,
                data: SearchHotKeyBean?,
            ): View {
                val itemBinding = ItemSearchHotKeyBinding.inflate(layoutInflater, parent, false)

                itemBinding.tvItem.text = data?.marqueeMessage()
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
                        data?.delete()
                        initSearchHistory()
                    }
                }
                itemBinding.root.noQuickClick {
                    search(data?.name ?: "")
                }
                return itemBinding.root
            }
        }
    }

    private fun saveSearchHotKey(data: SearchHotKeyBean) {
        lifecycleScope.launch {
            val save = async {
                SearchDb.saveSearchHistory(data)
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