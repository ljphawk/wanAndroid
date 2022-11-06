package com.ljp.module_base.router.actiondata

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/*
 *@创建者       L_jp
 *@创建时间     2022/7/22 16:56.
 *@描述
 */
@Parcelize
data class CollectActionData(val articleId: Long, val collect: Boolean) : Parcelable