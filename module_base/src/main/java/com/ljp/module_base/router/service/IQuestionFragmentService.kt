package com.ljp.module_base.router.service

import androidx.fragment.app.Fragment
import com.therouter.inject.Singleton


/*
 *@创建者       L_jp
 *@创建时间     2022/11/5 10:32.
 *@描述
 */
@Singleton
interface IQuestionFragmentService {

    fun getFragment(): Fragment
}