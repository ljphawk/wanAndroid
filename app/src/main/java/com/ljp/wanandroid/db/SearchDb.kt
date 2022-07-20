package com.ljp.wanandroid.db

import com.ljp.wanandroid.model.SearchHotKeyBean
import org.litepal.LitePal


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 16:25.
 *@描述
 */
object SearchDb {

    fun getSearchHistory(): MutableList<SearchHotKeyBean> {
        return LitePal.order("id desc").find(SearchHotKeyBean::class.java) ?: mutableListOf()
    }

    fun clearSearchHistory() {
        LitePal.deleteAll(SearchHotKeyBean::class.java)
    }

    fun saveSearchHistory(data: SearchHotKeyBean) {
        val list = LitePal.where("name = ?", data.name).find(SearchHotKeyBean::class.java)
        list.forEach {
            it.delete()
        }
        data.save()
    }

    fun deleteLastData() {
        LitePal.findLast(SearchHotKeyBean::class.java).delete()
    }
}