package com.ljp.wanandroid.ui.fragment.test.tab1

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import com.ljp.wanandroid.databinding.FragmentTab1Binding
import com.ljp.wanandroid.ui.activity.test.TestViewModel
import com.ljp.module_base.ui.BaseBindingFragment
import com.ljp.respository.extensions.launch
import com.ljp.respository.extensions.launchAndCollect
import com.ljp.respository.extensions.launchAndCollectIn
import com.ljp.respository.network.livadata.createObserver
import com.ljp.lib_base.utils.LOG
import dagger.hilt.android.AndroidEntryPoint


/*
 *@创建者       L_jp
 *@创建时间     2022/10/16 13:42.
 *@描述
 */
@AndroidEntryPoint
class TabFragment1 : BaseBindingFragment<FragmentTab1Binding>() {

    private val testViewModel by activityViewModels<TestViewModel>()

    override fun initData(view: View, savedInstanceState: Bundle?) {
        initViewListener()
        initFlowState()
    }

    private fun initFlowState() {
        //请求方式1
        testViewModel.homeTopArticle.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            onSuccess = {
                setRequestText("请求方式1的结果数据: \n${it.toString()}")
            }
            onFailed = { code, message ->

            }
        }
        testViewModel.homeTopArticle2.launchAndCollectIn(this) {
            onSuccess = {
                setRequestText("请求方式2的结果数据: \n${it.toString()}")
            }
            onFailed = { code, message ->

            }
        }
        testViewModel.homeHotArticle2.observe(this, createObserver(
            onSuccess = {
                setRequestText("请求方式4的结果数据: \n$it")
            },
            onFailed = {

            }
        ))
    }

    private fun setRequestText(content: String?) {
        LOG.d("===ljp", content)
        binding.tvContent.text = content
    }

    private fun initViewListener() {
        binding.btRequest1.setOnClickListener {
            launch {
                testViewModel.getHomeTopArticle()
            }
        }
        binding.btRequest2.setOnClickListener {
            launch {
                testViewModel.getHomeTopArticle2()
            }
        }
        binding.btRequest3.setOnClickListener {
            requestFunction()
        }
        binding.btRequest4.setOnClickListener {
            testViewModel.getHomeHotArticle2(1)
        }

    }

    private fun requestFunction() {
        launchAndCollect({ testViewModel.getHomeBannerList() }) {
            onSuccess = {
                setRequestText("请求方式3的结果数据:\n ${it.toString()}")
            }
            onFailed = { code, message ->

            }
        }

    }

}