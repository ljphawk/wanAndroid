package com.ljp.module_base.app

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import com.ljp.lib_base.app.ApplicationDelegate


/*
 *@创建者       L_jp
 *@创建时间     2020/7/19 10:44.
 *@描述
 */
open class BaseApplication : Application() {

    companion object {
        lateinit var instance: BaseApplication
        private const val MODULE_META_KEY = "ApplicationDelegate";
    }

    private val appDelegateList = mutableListOf<ApplicationDelegate>()

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        findApplicationDelegate(base);

        appDelegateList.forEach {
            it.attachBaseContext(this, base)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        
        appDelegateList.forEach {
            it.onCreate(this)
        }
    }

    private fun findApplicationDelegate(context: Context) {
        appDelegateList.clear()
        try {
            val pm = context.packageManager
            val packageName = context.packageName
            val info = pm.getApplicationInfo(packageName, GET_META_DATA)
            if (info.metaData != null) {
                for (key in info.metaData.keySet()) {
                    val value = info.metaData[key]
                    if (MODULE_META_KEY == value) {
                        val delegate = initApplicationDelegate(key)
                        appDelegateList.add(delegate)
                    }
                }
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun initApplicationDelegate(className: String): ApplicationDelegate {
        val clazz: Class<*> = try {
            Class.forName(className)
        } catch (e: ClassNotFoundException) {
            throw IllegalArgumentException("找不到$className", e)
        }
        val instance = try {
            clazz.newInstance()
        } catch (e: Exception) {
            throw RuntimeException("不能获取 $className 的实例", e)
        }
        if (instance !is ApplicationDelegate) {
            throw RuntimeException("$instance 不是ApplicationDelegate实现类")
        }
        return instance
    }
}