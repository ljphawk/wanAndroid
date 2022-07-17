package com.ljp.wanandroid.model

import com.qszx.utils.extensions.contentHasValue


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 11:30.
 *@描述
 */
data class HomeArticleListBean(
    var datas: MutableList<HomeArticleBean>,
    val curPage: Int,
    val pageCount: Int,
    val over: Boolean,
)

data class HomeArticleBean(
    val link: String?,
    val author: String?,//作者
    val shareUser: String?,//分享者
    val desc: String?,//
    val title: String?,//
    val chapterName: String?,//课程推荐
    val superChapterName: String?,//
    val collect: Boolean,//
    val id: Long,//
    val fresh: Boolean,//
    val niceDate: String?,//
    val tags: MutableList<HomeArticleTagBean>?,//
) {
    fun getAuthorText(): String {
        return if (author.contentHasValue()) {
            "作者:${author}"
        } else if (shareUser.contentHasValue()) {
            "分享人:${shareUser}"
        } else {
            "无"
        }
    }
}

data class HomeArticleTagBean(val name: String?)