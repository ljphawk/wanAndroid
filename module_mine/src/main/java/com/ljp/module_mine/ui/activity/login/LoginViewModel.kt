package com.ljp.module_mine.ui.activity.login

import com.ljp.module_base.network.repository.UserRepository
import com.ljp.module_base.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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