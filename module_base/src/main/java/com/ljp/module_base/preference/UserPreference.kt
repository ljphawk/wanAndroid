package com.ljp.module_base.preference

import com.ljp.repository.constant.UrlConstant
import com.ljp.repository.extensions.parcelableValue
import com.ljp.lib_base.extensions.contentHasValue
import com.ljp.module_base.network.data.UserBean
import com.ljp.repository.extensions.stringValue
import com.ljp.repository.preference.BasePreference
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


    fun isLogin(): Boolean {
        return userLoginPassword.contentHasValue()
    }

    var userInfo by parcelableValue<UserBean>()

    var userLoginName by stringValue()

    var userLoginPassword by stringValue()


    fun setUserInfoData(info: UserBean, pwd: String) {
        userInfo = info
        userLoginName = info.username
        userLoginPassword = pwd
    }

    fun getUserAvatarUrl(): String {
        return UrlConstant.getAvatarUrl("${userInfo?.id}")
    }

    fun logout() {
        clearAll()
    }
}
