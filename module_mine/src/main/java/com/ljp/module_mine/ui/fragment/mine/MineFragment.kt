package com.ljp.module_mine.ui.fragment.mine

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.example.lib_imageload.glide.loadImage
import com.ljp.lib_base.utils.showToast
import com.ljp.module_base.preference.UserPreference
import com.ljp.module_base.router.RouterAction
import com.ljp.module_base.router.RouterPath
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.module_mine.R
import com.ljp.module_mine.data.MineItemBean
import com.ljp.module_mine.databinding.FragmentMineBinding
import com.ljp.module_mine.databinding.ItemMineRvBinding
import com.therouter.TheRouter
import com.therouter.router.Route
import com.therouter.router.action.interceptor.ActionInterceptor


/*
 *@创建者       L_jp
 *@创建时间     2022/11/6 11:04.
 *@描述
 */
@Route(path = RouterPath.PATH_MINE)
class MineFragment : BaseBindingFragment<FragmentMineBinding>() {

    private val motionLayoutProgressActionInterceptor = object : ActionInterceptor() {
        override fun handle(context: Context, args: Bundle): Boolean {
            binding.motionLayout.progress = args.getFloat("progress")
            return false
        }
    }

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        setUserInfo()
        TheRouter.addActionInterceptor(RouterAction.ACTION_DRAWER_LAYOUT_PROGRESS,
            motionLayoutProgressActionInterceptor)
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
            MineItemBean("收藏文章", R.drawable.login_icon_pwd_open),
            MineItemBean("分享文章", R.drawable.login_icon_pwd_open),
            MineItemBean("我的积分", R.drawable.login_icon_pwd_open),
            MineItemBean("待办清单", R.drawable.login_icon_pwd_open),
            MineItemBean("设置", R.drawable.login_icon_pwd_open),
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        TheRouter.removeActionInterceptor(RouterAction.ACTION_DRAWER_LAYOUT_PROGRESS,
            motionLayoutProgressActionInterceptor)
    }
}