package com.ljp.wanandroid.ui.fragment.hot

import com.ljp.wanandroid.constant.UrlConstant
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.glide.loadImage
import com.ljp.wanandroid.model.HomeArticleBean
import com.qszx.base.ui.BaseBindingQuickAdapter


/*
 *@创建者       L_jp
 *@创建时间     2022/7/7 12:33.
 *@描述
 */
class HotArticleAdapter : BaseBindingQuickAdapter<HomeArticleBean, ItemHotArticleViewBinding>() {

    override fun convert(holder: BaseBindingHolder, item: HomeArticleBean) {
        holder.getViewBinding<ItemHotArticleViewBinding>().apply {
            civAvatar.loadImage(UrlConstant.getAvatarUrl(item.id.toString()))
            tvAuthor.text = item.getAuthorText()
            tvTime.text = item.niceDate
            tvTitle.text = item.title
            tvDesc.text = item.desc
        }

    }
}