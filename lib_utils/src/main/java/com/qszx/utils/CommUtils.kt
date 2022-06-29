package com.qszx.utils

import android.app.Activity
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.qszx.utils.extensions.contentHasValue


/*
 *@创建者       L_jp
 *@创建时间     2020/3/18 19:24.
 *@描述        常用工具类 整合
 */
object CommUtils {

    /**
     * 隐藏软键盘
     * return 无用可忽略
     */
    fun hideSoftKeyBoard(activity: Activity?) {
        try {
            val view = activity?.currentFocus;
            if (view != null) {
                val inputMethodManager =
                    activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(
                    view.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 隐藏软键盘
     */
    fun hideSoftKeyBoard(view: View) {
        try {
            val inputMethodManager =
                view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    fun hideSoftKeyBoard(context: Context?, viewList: MutableList<View>) {
        if (context == null) {
            return
        }
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        viewList.forEach {
            inputMethodManager.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            );
        }

    }


    // 显示软键盘
    fun showInputManager(context: Context?) {
        if (context == null) {
            return
        }
        try {
            if ((context as Activity).currentFocus == null) {
                return
            }
            if (context.currentFocus?.windowToken == null) {
                return
            }
            val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInputFromWindow(
                context.currentFocus!!.windowToken,
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 显示软键盘
    fun showInputManager(context: Context?, view: View) {
        if (context == null) {
            return
        }
        try {
            val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //
    /**
     * 复制
     */
    fun copy(context: Context, content: String?): Boolean {
        if (!content.contentHasValue()) {
            return false
        }
        return try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clipData = ClipData.newPlainText(null, content)             // 获取系统剪贴板
            clipboard?.setPrimaryClip(clipData)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    /**
     * 拨打电话
     * 跳转到拨号界面，用户手动点击拨打
     */
    fun callPhone(context: Context, phone: String?) {
        if (phone.isNullOrEmpty()) {
            return
        }
        try {
            val intent = Intent(Intent.ACTION_DIAL);
            intent.data = Uri.parse("tel:$phone");
            context.startActivity(intent);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

