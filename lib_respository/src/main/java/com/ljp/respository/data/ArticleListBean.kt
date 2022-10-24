package com.ljp.respository.data

import com.ljp.lib_base.extensions.contentHasValue


/*
 *@创建者       L_jp
 *@创建时间     2022/7/6 11:30.
 *@描述
 */
data class ArticleListBean(
    var datas: MutableList<ArticleBean>,
    val curPage: Int,
    val pageRetrofitClientCount: Int,
    val over: Boolean,
)

data class ArticleBean(
    val link: String?,
    val author: String?,//作者
    val shareUser: String?,//分享者
    val desc: String?,//
    val title: String?,//
    val superChapterId: Long,//一级分类Id
    val superChapterName: String?,//一级分类
    val chapterId: Long,//二级分类Id
    val chapterName: String?,//二级分类
    var collect: Boolean,//是否收藏
    val id: Long,//
    val type: Int,
    val fresh: Boolean,//新
    val niceDate: String?,//
    val tags: MutableList<HomeArticleTagBean>?,//标签
    val zan: Int,//赞的数量
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

    fun isTop(): Boolean {
        return type == 1
    }

    fun getClassifyName(): String {
        return if (!chapterName.contentHasValue() && !superChapterName.contentHasValue()) {
            ""
        } else if (!chapterName.contentHasValue()) {
            "$superChapterName"
        } else {
            "$superChapterName / $chapterName"
        }
    }
}

data class HomeArticleTagBean(val name: String?)