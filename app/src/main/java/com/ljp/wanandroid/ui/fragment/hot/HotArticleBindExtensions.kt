package com.ljp.wanandroid.ui.fragment.hot

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.ljp.wanandroid.constant.UrlConstant
import com.ljp.wanandroid.databinding.ItemHotArticleHeadViewBinding
import com.ljp.wanandroid.databinding.ItemHotArticleViewBinding
import com.ljp.wanandroid.glide.loadImage
import com.ljp.wanandroid.model.HomeArticleBean
import com.ljp.wanandroid.model.HomeBannerBean
import com.ljp.wanandroid.ui.fragment.home.HomeBannerAdapter
import com.qszx.utils.showToast


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 17:10.
 *@描述
 */

fun ItemHotArticleHeadViewBinding.binding(
    context: Context,
    lifecycle: LifecycleOwner,
    listData: MutableList<HomeBannerBean>
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

fun ItemHotArticleViewBinding.binding(data: HomeArticleBean) {
    civAvatar.loadImage(UrlConstant.getAvatarUrl(data.id.toString()))
    tvAuthor.text = data.getAuthorText()
    tvTime.text = data.niceDate
    tvTitle.text = data.title
    tvDesc.text = data.desc
}