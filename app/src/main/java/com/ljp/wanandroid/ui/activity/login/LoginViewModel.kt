package com.ljp.wanandroid.ui.activity.login

import com.ljp.wanandroid.model.WxArticleBean
import com.ljp.wanandroid.network.base.ApiResponse
import com.ljp.wanandroid.network.repository.UserRepository
import com.qszx.base.ui.BaseViewModel
import com.qszx.respository.network.BaseApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


/*
 *@创建者       L_jp
 *@创建时间     2022/6/13 11:25.
 *@描述
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val userRepository: UserRepository): BaseViewModel() {


    /**
     * 登录
     */
    suspend fun login(name: String, pwd: String) = userRepository.login(name, pwd)

    /**
     * 注册
     */
    suspend fun register(name: String, pwd: String) = userRepository.register(name, pwd)

    /**
     * 退出
     */
    suspend fun logout() = userRepository.logout()


}