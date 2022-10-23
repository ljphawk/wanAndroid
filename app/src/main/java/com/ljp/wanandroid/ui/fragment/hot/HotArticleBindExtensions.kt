package com.ljp.wanandroid.ui.fragment.hot

import android.content.Context
import android.text.Html
import androidx.lifecycle.LifecycleOwner
import com.example.lib_imageload.glide.loadImage
import com.qszx.respository.constant.UrlConstant
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.qszx.respository.data.ArticleBean
import com.qszx.respository.data.HomeBannerBean
import com.ljp.wanandroid.ui.fragment.home.HomeBannerAdapter
import com.qszx.utils.RegexUtils
import com.qszx.utils.extensions.contentHasValue
import com.qszx.utils.extensions.show
import com.qszx.utils.showToast


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
