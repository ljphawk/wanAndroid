package com.ljp.wanandroid.eventbus

import android.app.Activity
import androidx.fragment.app.Fragment
import org.greenrobot.eventbus.EventBus


/*
 *@创建者       L_jp
 *@创建时间     2020/7/27 11:40.
 *@描述
 */
object AppEvent {

    private fun get(): EventBus {
        return EventBus.getDefault()
    }

    fun register(fragment: Fragment) {
        get().register(fragment)
    }

    fun register(activity: Activity) {
        get().register(activity)
    }

    fun unregister(fragment: Fragment) {
        get().unregister(fragment)
    }

    fun unregister(activity: Activity) {
        get().unregister(activity)
    }

    fun post(any: Any) {
        get().post(any)
    }
}