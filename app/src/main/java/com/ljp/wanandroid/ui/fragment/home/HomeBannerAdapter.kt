package com.ljp.wanandroid.ui.fragment.home

import com.dylanc.viewbinding.getBinding
import com.example.lib_imageload.glide.loadImage
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.ItemHomeBannerBinding
import com.qszx.respository.data.HomeBannerBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 11:46.
 *@描述
 */
class HomeBannerAdapter : BaseBannerAdapter<HomeBannerBean>() {

    override fun bindData(
        holder: BaseViewHolder<HomeBannerBean>,
        data: HomeBannerBean?,
        position: Int,
        pageSize: Int,
    ) {
        holder.getBinding<ItemHomeBannerBinding>().apply {
            ivItem.loadImage(data?.imagePath)
            tvTitle.text = data?.title
        }
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_home_banner
    }
}