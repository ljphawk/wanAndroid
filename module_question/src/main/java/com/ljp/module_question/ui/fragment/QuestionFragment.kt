package com.ljp.module_question.ui.fragment

import com.ljp.module_base.ui.BaseLazyBindingFragment
import com.ljp.module_question.databinding.FragmentQuestionBinding


/*
 *@创建者       L_jp
 *@创建时间     2022/7/4 15:52.
 *@描述
 */
class QuestionFragment : BaseLazyBindingFragment<FragmentQuestionBinding>() {

    companion object {

        fun newInstance(): QuestionFragment {
            return QuestionFragment()
        }
    }

    override fun loadData() {

    }
}