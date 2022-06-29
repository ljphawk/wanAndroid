package com.ljp.wanandroid.preference

import com.qszx.respository.BuildConfig
import com.qszx.respository.extensions.*
import com.qszx.respository.preference.BasePreference
import com.tencent.mmkv.MMKV


/*
 *@创建者       L_jp
 *@创建时间     6/5/21 4:13 PM.
 *@描述
 */

object ConfigPreference : BasePreference() {

    override fun create(): MMKV {
        return MMKV.mmkvWithID("config")!!
    }

    /**
     * app host status
     */
    var appHostStatus by stringValue(defaultValue = BuildConfig.BUILD_TYPE)

    var loginPhone by stringValue(defaultValue = "")
}
