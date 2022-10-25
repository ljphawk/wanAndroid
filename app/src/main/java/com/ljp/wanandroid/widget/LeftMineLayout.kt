package com.ljp.wanandroid.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import androidx.constraintlayout.motion.widget.MotionLayout
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.lib_imageload.glide.loadImage
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.ItemMineRvBinding
import com.ljp.wanandroid.databinding.LayoutLeftMineBinding
import com.ljp.wanandroid.data.MineItemBean
import com.ljp.repository.preference.UserPreference
import com.ljp.module_base.ui.BaseBindingLayout
import com.ljp.lib_base.utils.showToast


/*
 *@创建者       L_jp
 *@创建时间     2022/7/1 13:47.
 *@描述
 */
class LeftMineLayout(context: Context, attributeSet: AttributeSet) :
    BaseBindingLayout<LayoutLeftMineBinding>(context, attributeSet) {

    override fun attrId(): IntArray? {
        return null
    }

    override fun initData(typedArray: TypedArray?) {
        initRecyclerView()
        setUserInfo()
    }

    private fun setUserInfo() {
        val userInfo = UserPreference.userInfo
        binding.tvNikeName.text = userInfo?.nickname
        binding.tvUserName.text = userInfo?.username
        binding.civAvatar.loadImage(UserPreference.getUserAvatarUrl())
    }

    private fun initRecyclerView() {
        binding.recyclerView.linear().setup {
            addType<MineItemBean>(R.layout.item_mine_rv)
            onBind {
                val binding = ItemMineRvBinding.bind(itemView)
                binding.tvItem.text = getModel<MineItemBean>().item
                binding.ivIcon.setImageResource(getModel<MineItemBean>().res)
            }
            onClick(R.id.cl_item) {
                context.showToast(layoutPosition.toString())
            }
        }.models = getMineListData()
    }

    private fun getMineListData(): MutableList<MineItemBean> {
        return mutableListOf(
            MineItemBean("收藏文章", R.mipmap.ic_launcher),
            MineItemBean("分享文章", R.mipmap.ic_launcher),
            MineItemBean("我的积分", R.mipmap.ic_launcher),
            MineItemBean("待办清单", R.mipmap.ic_launcher),
            MineItemBean("设置", R.mipmap.ic_launcher),
        )
    }

    fun getMotionLayout(): MotionLayout {
        return binding.motionLayout
    }
}