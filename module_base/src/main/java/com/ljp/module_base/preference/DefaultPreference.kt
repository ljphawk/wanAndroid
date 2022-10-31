package com.ljp.module_base.preference

import com.ljp.repository.preference.BasePreference
import com.tencent.mmkv.MMKV


/*
 *@创建者       L_jp
 *@创建时间     6/7/21 11:31 AM.
 *@描述
 */
object DefaultPreference : BasePreference() {

    override fun create(): MMKV {
        return MMKV.defaultMMKV()!!
    }

}