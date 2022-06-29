package com.ljp.wanandroid.commonkey


/*
 *@创建者       L_jp
 *@创建时间     2021/8/4 13:21.
 *@描述
 */
object WebUrl {

    private val baseWebHost = EnvConstant.getWebHost()

    /**
     * 还车退款协议
     */
    val WEB_RETURN_CAR_AGREEMENT = baseWebHost + "business/businessPage/hcxy"
}