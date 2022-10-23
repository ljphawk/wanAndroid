package com.qszx.respository.data


/*
 *@创建者       L_jp
 *@创建时间     2022/6/20 14:24.
 *@描述
 */

/**
 * 数据模型 请放置 model包中
 */


class WxArticleBean {
    /**
     * id : 408
     * name : 鸿洋
     * order : 190000
     * visible : 1
     */
    var id = 0
    var name: String? = null
    var visible = 0

    override fun toString(): String {
        return "TestBean(id=$id, name=$name, visible=$visible)"
    }
}