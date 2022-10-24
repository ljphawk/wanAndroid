package com.ljp.respository.constant

import com.ljp.lib_base.utils.DateUtils


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 13:43.
 *@描述
 */
object UrlConstant {

    fun getAvatarUrl(path: String = DateUtils.getCurrentTimestamp().toString()): String {
        return "https://api.multiavatar.com/${path}.png"
    }

    fun getAvatarUrl2(path: String = DateUtils.getCurrentTimestamp().toString()): String {
        return "https://api.yimian.xyz/img?type=head&time=${path}"
    }
}