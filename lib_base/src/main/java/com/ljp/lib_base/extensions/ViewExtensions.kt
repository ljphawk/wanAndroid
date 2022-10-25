package com.ljp.lib_base.extensions

import android.view.View
import android.widget.EditText

/**
 * Created by ivan on 08/02/2018.
 */
fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.show(show: Boolean): Boolean {
    if (show) visible() else gone()
    return show
}

fun View.visible(visible: Boolean): Boolean {
    if (visible) visible() else invisible()
    return visible
}

fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun View.isGone(): Boolean = visibility == View.GONE

private var lastHashCode = 0

fun View.noQuickClick(delayTime: Long = 500, listener: (view: View) -> Unit) {

    var lastClickTime: Long = 0

    this.setOnClickListener {
        val curClickTime = System.currentTimeMillis()
        val hashCode = it.hashCode()
        //大于等于设置的时间,或者 不是同一个view, 可以触发事件点击
        if (curClickTime - lastClickTime >= delayTime || hashCode != lastHashCode) {
            listener(it)
        }
        lastHashCode = hashCode
        lastClickTime = curClickTime
    }
}

fun EditText.getContent(isTrim: Boolean = true): String {
    return if (isTrim) {
        text.toString().trim()
    } else {
        text.toString()
    }
}