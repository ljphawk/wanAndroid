package com.ljp.wanandroid.ui.test

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import com.qszx.utils.extensions.noQuickClick
import com.qszx.base.ui.BaseBindingActivity
import com.qszx.respository.extensions.launch
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.respository.extensions.launchAndCollectIn
import com.ljp.wanandroid.databinding.ActivityTestBinding


/*
 *@创建者       L_jp
 *@创建时间     2022/6/13 11:35.
 *@描述
 */
class TestActivity : BaseBindingActivity<ActivityTestBinding>() {

    private val testVm by viewModels<TestViewModel>()

    override fun initData(savedInstanceState: Bundle?) {

        binding.btLogin.noQuickClick {
            login()
        }

        binding.btGet.noQuickClick {
            launch { testVm.getWxarticleList() }
        }
        testVm.uiState.launchAndCollectIn(this, Lifecycle.State.STARTED) {
            onSuccess = {
                binding.tvContent.text = it.toString()
            }
            onFailed = { _, msg ->
                binding.tvContent.text = msg
            }

        }

        binding.btError.noQuickClick {
//            netError()
        }

    }

    private fun login() {
        launchAndCollect({ testVm.login("FastJetpack", "FastJetpack") }) {
            onSuccess = {
                binding.tvContent.text = it.toString()
            }
            onFailed = { _, errorMsg ->
                binding.tvContent.text = errorMsg
            }
        }
    }

//    private fun netError() {
//        launchAndCollect({ testVm.getNetDataError() }) {
//            onSuccess = {
//                binding.tvContent.text = it.toString()
//            }
//            onFailed = { _, errorMsg ->
//                binding.tvContent.text = errorMsg
//            }
//        }
//    }


}

