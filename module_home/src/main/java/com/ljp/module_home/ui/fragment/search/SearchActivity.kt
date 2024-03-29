package com.ljp.module_home.ui.fragment.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import com.ljp.lib_base.extensions.*
import com.ljp.lib_base.utils.CommUtils
import com.ljp.module_base.preference.ConfigPreference
import com.ljp.module_base.router.RouterPath
import com.ljp.module_base.ui.BaseBindingActivity
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.module_home.R
import com.ljp.module_home.databinding.ActivitySearchBinding
import com.ljp.module_home.databinding.ItemSearchHotKeyBinding
import com.ljp.module_home.ui.fragment.articlelist.ArticleListFragment
import com.ljp.module_home.ui.fragment.articlelist.ArticleListParams
import com.ljp.repository.dao.SearchHistoryDao
import com.ljp.repository.data.SearchHotKeyBean
import com.ljp.repository.extensions.parseList
import com.therouter.router.Autowired
import com.therouter.router.Route
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
@Route(path = RouterPath.PATH_SEARCH)
class SearchActivity : BaseBindingActivity<ActivitySearchBinding>() {

    @JvmField
    @Autowired
    var argsSearch: String? = null

    private val articleListFragment by lazy {
        ArticleListFragment.newInstance(ArticleListParams.getSearchParams())
    }

    override fun immersionBarView(): View {
        return binding.clSearch
    }

    override fun initData(savedInstanceState: Bundle?) {
        initViewListener()
        initSearchHotKey()
        initSearchHistory()
        initSearchEdittext()
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_container, articleListFragment)
            .commit()
    }

    private fun initViewListener() {
        binding.tvCancel.noQuickClick {
            finish()
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

        lifecycleScope.launch {
            delay(300)
            binding.etSearch.requestFocus()
            CommUtils.showInputManager(this@SearchActivity, binding.etSearch)
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
                        lifecycleScope.launch {
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