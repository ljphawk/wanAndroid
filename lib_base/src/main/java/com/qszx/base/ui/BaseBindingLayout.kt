package com.qszx.base.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.dylanc.viewbinding.base.ViewBindingUtil

/*
 *@创建者       L_jp
 *@创建时间     2019/9/19 9:36.
 *@描述
 */
abstract class BaseBindingLayout<VB : ViewBinding>  : FrameLayout {

    protected val TAG = this::class.java.simpleName


    private var _binding: VB? = null
    val binding: VB get() = _binding!!


    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @SuppressLint("ResourceType")
    private fun init(
        context: Context,
        attrs: AttributeSet?
    ) {
        isClickable = true

        _binding =  ViewBindingUtil.inflateWithGeneric(this, LayoutInflater.from(context), this, true)

        val attr = attrId()
        var typedArray: TypedArray? = null
        attr?.let {
            typedArray = context.obtainStyledAttributes(attrs, it)
        }
        initData(typedArray)
    }

    protected abstract fun attrId(): IntArray?

    protected abstract fun initData(typedArray: TypedArray?)

}