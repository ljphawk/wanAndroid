package com.ljp.wanandroid.ui.fragment.articlelist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 10:05.
 *@描述
 */


class ArticleListParams(val type: ArticleListClassify, val startIndexIsZero: Boolean = true)

interface ArticleListClassify : Parcelable

@Parcelize
data class HomeArticleParams(val type: HomeArticleType) : ArticleListClassify

enum class HomeArticleType {
    HOT,
    SQUARE,
    QUESTION
}
