package com.qszx.respository.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/*
 *@创建者       L_jp
 *@创建时间     2022/6/30 15:18.
 *@描述
 */
@Parcelize
data class UserBean(
    val coinCount: Int,
    val email: String?,
    val icon: String?,
    val id: Int?,
    val nickname: String?,
    val password: String?,
    val publicName: String?,
    val username: String?,
    val token: String?,
    val collectIds: MutableList<Long>?,
) : Parcelable