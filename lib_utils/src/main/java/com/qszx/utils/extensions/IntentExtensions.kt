package com.qszx.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.fragment.app.Fragment

/**
 * 获取参数
 * 例子 val link by getIntentParam<String>("link")
 */
inline fun <reified T> Activity.getIntentParam(key: String, default: T? = null) =
    lazy {
        val ret = when (T::class.java) {
            String::class.java -> intent.getStringExtra(key) ?: default
            Int::class.java, Integer::class.java -> intent.getIntExtra(key, (default as Int?) ?: 0)
            Float::class.java -> intent.getFloatExtra(key, (default as Float?) ?: 0f)
            Boolean::class.java -> intent.getBooleanExtra(key, (default as Boolean?) ?: false)
            else -> (intent.getParcelableExtra(key) as T?) ?: default
        }
        ret as T?
    }

inline fun <reified T> Fragment.getBundleParam(key: String, default: T? = null) =
    lazy {
        val ret = when (T::class.java) {
            String::class.java -> arguments?.getString(key) ?: default
            Int::class.java, Integer::class.java -> arguments?.getInt(key, (default as Int?) ?: 0)
            Float::class.java -> arguments?.getFloat(key, (default as Float?) ?: 0f)
            Boolean::class.java -> arguments?.getBoolean(key, (default as Boolean?) ?: false)
            else -> arguments?.getParcelable(key) as T? ?: default
        }
        ret as T?
    }


inline fun <reified T> Context.startActivity() {
    val intent = Intent(this, T::class.java)
    startActivity(intent)
}