package com.qszx.base.manager

import android.app.Activity
import com.qszx.utils.CommUtils
import java.lang.Exception
import java.util.*

object ActivityManager {

    private val activityList = Stack<Activity>()


    fun addActivity(activity: Activity) {
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity) {
        hideSoftKeyBoard(activity)
        activityList.remove(activity)
    }

    fun <T : Activity?> removeActivity(tClass: Class<T>) {
        for (activity in activityList) {
            if (tClass.name == activity.javaClass.name) {
                activity.finish()
                removeActivity(activity)
                return
            }
        }
    }

    fun getTopActivity(): Activity? {
        return try {
            activityList[activityList.size - 1]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun removeAllActivity() {
        for (activity in activityList) {
            hideSoftKeyBoard(activity)
            activity.finish()
        }
        activityList.clear()
    }

    fun <T : Activity?> hasActivity(tClass: Class<T>): Boolean {
        for (activity in activityList) {
            if (tClass.name == activity.javaClass.name) {
                return !activity.isDestroyed || !activity.isFinishing
            }
        }
        return false
    }

    private fun hideSoftKeyBoard(activity: Activity) {
        CommUtils.hideSoftKeyBoard(activity)
    }
}