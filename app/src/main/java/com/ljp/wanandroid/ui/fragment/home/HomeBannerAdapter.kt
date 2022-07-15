package com.ljp.wanandroid.ui.fragment.home

import android.widget.ImageView
import android.widget.TextView
import com.dylanc.viewbinding.getBinding
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.ItemHomeBannerBinding
import com.ljp.wanandroid.glide.loadImage
import com.ljp.wanandroid.model.HomeBannerBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 11:46.
 *@描述
 */
class HomeBannerAdapter : BaseBannerAdapter<HomeBannerBean>() {

    override fun bindData(holder: BaseViewHolder<HomeBannerBean>, data: HomeBannerBean?, position: Int, pageSize: Int) {
//        holder.getBinding<ItemHomeBannerBinding>().apply {
//            ivItem.loadImage(data?.imagePath)
//            tvTitle.text = data?.title
//        }
        holder.findViewById<ImageView>(R.id.iv_item).loadImage(data?.imagePath)
        holder.findViewById<TextView>(R.id.tv_title).text = data?.title
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_home_banner
    }
}