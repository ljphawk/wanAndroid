package com.ljp.wanandroid.ui.fragment.hot

import android.content.Context
import android.text.Html
import androidx.lifecycle.LifecycleOwner
import com.example.lib_imageload.glide.loadImage
import com.ljp.wanandroid.constant.UrlConstant
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.model.ArticleBean
import com.ljp.wanandroid.model.HomeBannerBean
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
    context: Context,
    lifecycle: LifecycleOwner,
    listData: MutableList<HomeBannerBean>,
) {
    if (bannerView.adapter == null) {
        bannerView.apply {
            setAdapter(HomeBannerAdapter())
            setLifecycleRegistry(lifecycle.lifecycle)
            setOnPageClickListener { _, position ->
                val data = bannerView.data[position] as HomeBannerBean?
                context.showToast(data?.url)
                //TODO
            }
        }.create(listData)
    } else {
        bannerView.refreshData(listData)
    }
}

fun ItemHotArticleViewBinding.binding( data: ArticleBean) {
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
