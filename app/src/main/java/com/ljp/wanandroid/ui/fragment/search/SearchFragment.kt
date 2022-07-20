package com.ljp.wanandroid.ui.fragment.search

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.FragmentSearchBinding
import com.ljp.wanandroid.db.SearchDb
import com.ljp.wanandroid.model.SearchHotKeyBean
import com.ljp.wanandroid.preference.ConfigPreference
import com.ljp.wanandroid.utils.LOG
import com.qszx.base.ui.BaseBindingFragment
import com.qszx.respository.extensions.parseList
import com.qszx.utils.extensions.*
import com.qszx.utils.showToast
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 13:19.
 *@描述
 */
@AndroidEntryPoint
class SearchFragment : BaseBindingFragment<FragmentSearchBinding>() {

    private val searchViewModel by viewModels<SearchViewModel>()

    override fun immersionBarView(): View {
        return binding.llSearch
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initViewListener()
        initSearchHotKey()
        initSearchHistory()
    }

    private fun initViewListener() {
        binding.tvCancel.noQuickClick {
        }
        binding.tvClearHistory.noQuickClick {
            SearchDb.clearSearchHistory()
            initSearchHistory()
        }
        binding.etSearch
    }

    private fun initSearchHotKey() {
        lifecycleScope.launch {
            val data =
                withContext(Dispatchers.IO) { parseList(ConfigPreference.searchHotKey, SearchHotKeyBean::class.java) }

            binding.hotKeyFlowlayout.adapter = object : TagAdapter<SearchHotKeyBean>(data) {
                override fun getView(parent: FlowLayout?, position: Int, t: SearchHotKeyBean?): View {
                    val tvKey = layoutInflater.inflate(R.layout.item_search_hot_key, parent, false) as TextView
                    tvKey.text = t?.marqueeMessage()
                    return tvKey
                }
            }
            binding.hotKeyFlowlayout.setOnTagClickListener { _, position, _ ->
                saveSearchHotKey(data[position].copy())
                showToast(data[position].toString())
                true
            }
        }
    }

    private fun initSearchHistory() {
        lifecycleScope.launch {
            val data = withContext(Dispatchers.IO) { SearchDb.getSearchHistory() }
            binding.llSearchHistory.show(data.hasContent())
            if (!data.hasContent()) {
                return@launch
            }
            binding.historyFlowlayout.adapter = object : TagAdapter<SearchHotKeyBean>(data) {
                override fun getView(parent: FlowLayout?, position: Int, t: SearchHotKeyBean?): View {
                    val tvKey = layoutInflater.inflate(R.layout.item_search_hot_key, parent, false) as TextView
                    tvKey.text = t?.marqueeMessage()
                    return tvKey
                }
            }
            binding.historyFlowlayout.setOnTagClickListener { _, position, _ ->
                saveSearchHotKey(data[position].copy())
                showToast(data[position].toString())
                true
            }
        }
    }



    private fun saveSearchHotKey(data: SearchHotKeyBean) {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                val count = binding.historyFlowlayout.adapter?.count ?: 0
                if (count >= 20) {
                    SearchDb.deleteLastData()
                }
                SearchDb.saveSearchHistory(data)
            }
            initSearchHistory()
        }
    }

    private fun search() {
    }
}