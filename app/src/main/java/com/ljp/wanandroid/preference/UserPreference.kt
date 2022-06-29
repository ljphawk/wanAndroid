package com.ljp.wanandroid.preference

import com.qszx.utils.extensions.contentHasValue
import com.qszx.respository.extensions.stringValue
import com.qszx.respository.preference.BasePreference
import com.tencent.mmkv.MMKV


/*
 *@创建者       L_jp
 *@创建时间     6/5/21 4:13 PM.
 *@描述
 */

object UserPreference : BasePreference() {


    override fun create(): MMKV {
        return MMKV.mmkvWithID("user")!!
    }

    var userId by stringValue()

    var token by stringValue()

    fun isLogin(): Boolean {
        return token.contentHasValue()
    }

    fun logout() {
        clearAll()
    }
}
