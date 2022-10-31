package com.ljp.wanandroid.ui.fragment.hot

import android.text.Html
import androidx.lifecycle.LifecycleOwner
import com.example.lib_imageload.glide.loadImage
import com.ljp.repository.constant.UrlConstant
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.module_base.network.data.ArticleBean
import com.ljp.wanandroid.ui.fragment.home.HomeBannerAdapter
import com.ljp.lib_base.utils.RegexUtils
import com.ljp.lib_base.extensions.contentHasValue
import com.ljp.lib_base.extensions.show
import com.ljp.module_base.network.data.HomeBannerBean


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 17:10.
 *@描述
 */

fun ItemHotArticleHeadViewBinding.binding(
    lifecycle: LifecycleOwner,
    listData: MutableList<HomeBannerBean>,
    itemClick: (url: String?) -> Unit,
) {
    if (bannerView.adapter == null) {
        bannerView.apply {
            setAdapter(HomeBannerAdapter())
            setLifecycleRegistry(lifecycle.lifecycle)
            setOnPageClickListener { _, position ->
                val data = bannerView.data[position] as HomeBannerBean?
                itemClick.invoke(data?.url)
            }
        }.create(listData)
    } else {
        bannerView.refreshData(listData)
    }
}

fun ItemHotArticleViewBinding.binding(data: ArticleBean) {
    civAvatar.loadImage(UrlConstant.getAvatarUrl(data.getAuthorText()))
    tvAuthor.text = data.getAuthorText()
    tvTime.text = data.niceDate
    tvTitle.text = Html.fromHtml(data.title)
    val desc = RegexUtils.removeAllBank(Html.fromHtml(data.desc).toString())
    if (tvDesc.show(desc.contentHasValue())) {
        tvDesc.text = desc
    }
    tvTop.show(data.isTop())
    tvNew.show(data.fresh)
    val classifyName = data.getClassifyName()
    if (tvTag.show(classifyName.contentHasValue())) {
        tvTag.text = classifyName
    }

    ivCollect.isActivated = data.collect
}
