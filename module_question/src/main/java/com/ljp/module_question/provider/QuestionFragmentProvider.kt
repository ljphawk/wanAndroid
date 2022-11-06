package com.ljp.module_question.provider

import androidx.fragment.app.Fragment
import com.ljp.module_base.router.service.IQuestionFragmentService
import com.ljp.module_question.ui.fragment.QuestionFragment
import com.therouter.inject.ServiceProvider


/*
 *@创建者       L_jp
 *@创建时间     2022/11/5 10:41.
 *@描述
 */
@ServiceProvider
fun getQuestionFragmentServiceImpl(): IQuestionFragmentService {
    return object : IQuestionFragmentService {
        override fun getFragment(): Fragment {
            return QuestionFragment.newInstance()
        }
    }
}