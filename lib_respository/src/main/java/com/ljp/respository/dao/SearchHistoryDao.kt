package com.ljp.respository.dao

import com.ljp.respository.data.SearchHotKeyBean
import com.ljp.lib_base.extensions.contentHasValue
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 16:25.
 *@描述
 */
object SearchHistoryDao {

    /**
     * 历史记录最多显示的条数
     */
    private const val maxShowHistory = 20

    /**
     * 获取历史记录  id 倒序
     */
    fun getSearchHistory(): MutableList<String> {
        val list = LitePal.order("id desc").find(SearchHotKeyBean::class.java) ?: mutableListOf()
        val showList = mutableListOf<String>()
        list.forEach {
            if (it.name.contentHasValue() && showList.size < maxShowHistory) {
                showList.add(it.name!!)
            }
        }
        return showList
    }

    fun clearSearchHistory() {
        LitePal.deleteAll(SearchHotKeyBean::class.java)
    }

    fun saveSearchHistory(data: SearchHotKeyBean): Boolean {
        val lastData = LitePal.findLast(SearchHotKeyBean::class.java)
        if (lastData != null && lastData.name == data.name) {
            return false
        }

        val list = LitePal.where("name = ?", data.name).find(SearchHotKeyBean::class.java)
        list.forEach {
            it.delete()
        }
        return data.save()
    }


    suspend fun removeSearchHistory(data: String) {
        val list = LitePal.where("name = ?", data).find(SearchHotKeyBean::class.java)
        list.forEach {
            it.delete()
        }
    }


}