package com.ljp.lib_base.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager


object ScreenUtils {
    /**
     * screenWidth
     * @param context
     * @return
     */
    fun getScreenWidth(
        context: Context,
        displayMetrics: DisplayMetrics = getDisplayMetrics(
            context
        )
    ): Int {
        return displayMetrics.widthPixels
    }

    /**
     * screenHeight
     * @param context
     * @return
     */
    fun getScreenHeight(
        context: Context,
        displayMetrics: DisplayMetrics = getDisplayMetrics(
            context
        )
    ): Int {
        return displayMetrics.heightPixels
    }

    private fun getDisplayMetrics(context: Context): DisplayMetrics {
        return context.resources.displayMetrics
    }

    /**
     * screenWidth 的dp
     * @param context
     * @return
     */
    fun getScreenWidthDp(context: Context): Float {
        val dm = getDisplayMetrics(context)
        val density: Float = dm.density
        val width = getScreenWidth(context, dm)
        return (width / density)
    }

    /**
     * screenHeight 的dp
     * @param context
     * @return
     */
    fun getScreenHeightDp(context: Context): Float {
        val dm = getDisplayMetrics(context)
        val density: Float = dm.density
        val height = getScreenHeight(context, dm)
        return (height / density)
    }


    /**
     * 判断是否为平板
     */
    fun isPad(context: Context): Boolean {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val dm = DisplayMetrics()
        display.getMetrics(dm)
        val x = Math.pow((dm.widthPixels / dm.xdpi).toDouble(), 2.0)
        val y = Math.pow((dm.heightPixels / dm.ydpi).toDouble(), 2.0)
        // 屏幕尺寸
        val screenInches = Math.sqrt(x + y)
        // 大于7尺寸则为Pad
        return screenInches > 7.0
    }

}