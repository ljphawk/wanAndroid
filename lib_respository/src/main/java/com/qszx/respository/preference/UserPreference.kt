package com.qszx.respository.preference

import com.qszx.respository.constant.UrlConstant
import com.qszx.respository.data.UserBean
import com.qszx.respository.extensions.parcelableValue
import com.qszx.utils.extensions.contentHasValue
import com.qszx.respository.extensions.stringValue
import com.qszx.respository.preference.base.BasePreference
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
