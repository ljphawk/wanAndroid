package com.ljp.module_home.ui.fragment.articlelist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/*
 *@创建者       L_jp
 *@创建时间     2022/7/20 10:05.
 *@描述
 */

@Parcelize
class ArticleListParams(
    val type: ArticleListClassify,
    val isAutoRefresh: Boolean = true,
    val startIndexIsZero: Boolean = true,
): Parcelable {
    companion object {

        fun getHomeParams(type: HomeArticleType): ArticleListParams {
            return ArticleListParams(HomeArticleParams(type))
        }


        fun getSearchParams(): ArticleListParams {
            return ArticleListParams(SearchArticleParams(), false)
        }
    }
}

interface ArticleListClassify : Parcelable


/**
 * 首页文章列表
 */
@Parcelize
data class HomeArticleParams(val type: HomeArticleType) : ArticleListClassify

enum class HomeArticleType {
    HOT,
    SQUARE,
    QUESTION
}


/**
 * 搜索内容文章列表
 */
@Parcelize
data class SearchArticleParams(var content: String = "") : ArticleListClassify