package com.ljp.wanandroid.db

import com.ljp.wanandroid.model.SearchHotKeyBean
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 16:25.
 *@描述
 */
object SearchDb {

    /**
     * 历史记录最多显示的条数
     */
    private const val maxShowHistory = 20

    /**
     * 获取历史记录  id 倒序
     */
    fun getSearchHistory(): MutableList<SearchHotKeyBean> {
        val list = LitePal.order("id desc").find(SearchHotKeyBean::class.java) ?: mutableListOf()
        return if (list.size >= maxShowHistory) {
            list.subList(0, maxShowHistory)
        } else {
            list
        }
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


}