package com.ljp.wanandroid.model

import com.sunfusheng.marqueeview.IMarqueeItem
import org.litepal.crud.LitePalSupport


/*
 *@创建者       L_jp
 *@创建时间     2022/7/19 14:48.
 *@描述
 */
data class SearchHotKeyBean(
    var name: String?,
) : LitePalSupport(),IMarqueeItem {
    override fun marqueeMessage(): CharSequence {
        return name.toString()
    }
}