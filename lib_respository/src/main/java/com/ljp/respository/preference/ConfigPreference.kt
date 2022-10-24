package com.ljp.respository.preference

import com.ljp.respository.BuildConfig
import com.ljp.respository.extensions.*
import com.ljp.respository.preference.base.BasePreference
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

    var searchHotKey by stringValue()
}
