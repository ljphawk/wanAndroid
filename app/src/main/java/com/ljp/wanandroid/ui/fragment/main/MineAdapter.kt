package com.ljp.wanandroid.ui.fragment.main

import com.ljp.wanandroid.databinding.ItemMineRvBinding
import com.ljp.wanandroid.model.MineItemBean
import com.qszx.base.ui.BaseBindingQuickAdapter


/*
 *@创建者       L_jp
 *@创建时间     2022/7/7 11:34.
 *@描述
 */
class MineAdapter(mineListData: MutableList<MineItemBean>) :
    BaseBindingQuickAdapter<MineItemBean, ItemMineRvBinding>(data = mineListData) {

    override fun convert(holder: BaseBindingHolder, item: MineItemBean) {
        holder.getViewBinding<ItemMineRvBinding>().apply {
            tvItem.text = item.item
            ivIcon.setImageResource(item.res)
        }
    }
}