package com.ljp.module_base.extensions

import com.therouter.TheRouter


/*
 *@创建者       L_jp
 *@创建时间     2022/11/5 20:11.
 *@描述
 */

fun theStartWebActivity(path: String, url: String?) {
    TheRouter.build(path)
        .withString("argsUrl", url)
        .navigation()

}

fun theStartActivity(path: String) {
    TheRouter.build(path)
        .navigation()
}