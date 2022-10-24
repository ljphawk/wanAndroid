package com.ljp.module_base.ui

import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dylanc.viewbinding.base.ViewBindingUtil


/*
 *@创建者       L_jp
 *@创建时间     2022/6/20 16:44.
 *@描述
 */
abstract class BaseBindingQuickAdapter<T, VB : ViewBinding>(layoutResId: Int = -1, data: MutableList<T>? = null) :
    BaseQuickAdapter<T, BaseBindingQuickAdapter.BaseBindingHolder>(layoutResId, data) {

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int) =
        BaseBindingHolder(ViewBindingUtil.inflateWithGeneric<VB>(this, parent))

    class BaseBindingHolder(private val binding: ViewBinding) : BaseViewHolder(binding.root) {
        constructor(itemView: View) : this(ViewBinding { itemView })

        @Suppress("UNCHECKED_CAST")
        fun <VB : ViewBinding> getViewBinding() = binding as VB
    }
}