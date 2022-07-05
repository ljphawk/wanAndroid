package com.ljp.wanandroid.ui.activity.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.ljp.wanandroid.R
import com.ljp.wanandroid.databinding.ActivityLoginBinding
import com.ljp.wanandroid.extensions.addTextChangedListener
import com.ljp.wanandroid.extensions.setAnimStatusListener
import com.ljp.wanandroid.preference.UserPreference
import com.ljp.wanandroid.ui.activity.main.MainActivity
import com.ljp.wanandroid.utils.SpanUtils
import com.qszx.base.ui.BaseBindingActivity
import com.qszx.respository.extensions.launchAndCollect
import com.qszx.utils.CommUtils
import com.qszx.utils.extensions.*
import com.qszx.utils.showToast


/*
 *@创建者       L_jp
 *@创建时间     2022/6/29 10:20.
 *@描述
 */
class LoginActivity : BaseBindingActivity<ActivityLoginBinding>() {

    private val loginViewModel by viewModels<LoginViewModel>()

    private var isRegisterView = false

    override fun initData(savedInstanceState: Bundle?) {
        setRegisterText()
        initViewListener()
    }

    private fun initViewListener() {
        binding.etUserName.addTextChangedListener {
            binding.ivUserNameClear.show(it.contentHasValue())
        }

        binding.etPwd.addTextChangedListener {
            binding.ivPwdEyes.show(it.contentHasValue())
        }

        binding.etAgainPwd.addTextChangedListener {
            binding.ivAgainPwdEyes.show(it.contentHasValue())
        }

        binding.ivUserNameClear.noQuickClick {
            binding.etUserName.text.clear()
        }
        setPwdShowAndHide(binding.ivPwdEyes, binding.etPwd)
        setPwdShowAndHide(binding.ivAgainPwdEyes, binding.etAgainPwd)

        binding.motionLayout.setAnimStatusListener {
            onEndAnimCompleted = {
                binding.etAgainPwd.text.clear()
                CommUtils.hideSoftKeyBoard(binding.etAgainPwd)
            }
        }

        binding.btLogin.noQuickClick {
            if (isRegisterView) {
                requestRegister()
            } else {
                requestLogin()
            }
        }
    }

    private fun requestLogin() {
        val userName = binding.etUserName.getContent()
        val password = binding.etPwd.getContent()
        if (!userName.contentHasValue() || !password.contentHasValue()) {
            showToast("请输入账号或者密码!")
            return
        }
        launchAndCollect({ loginViewModel.login(userName, password) }) {
            onSuccess = { data ->
                data?.let {
                    UserPreference.setUserInfoData(it, password)
                    startActivity<MainActivity>()
                    finish()
                }
            }
        }
    }


    private fun requestRegister() {
        val userName = binding.etUserName.getContent()
        val password = binding.etPwd.getContent()
        val againPwd = binding.etAgainPwd.getContent()
        if (!userName.contentHasValue() || !password.contentHasValue() || !againPwd.contentHasValue()) {
            showToast("请输入账号或者密码!")
            return
        }
        if (password != againPwd) {
            showToast("两次密码输入不一致!")
            return
        }
        launchAndCollect({ loginViewModel.register(userName, password) }) {
            onSuccess = { data ->
                data?.let {
                    showToast("注册成功~")
                    registerSpanTextClick()
                }
            }
        }
    }

    private fun setPwdShowAndHide(imageView: ImageView, editText: EditText) {
        imageView.setOnClickListener {
            val isActivated = imageView.isActivated
            editText.transformationMethod = if (isActivated) {
                PasswordTransformationMethod.getInstance()
            } else {
                HideReturnsTransformationMethod.getInstance()
            }
            editText.setSelection(editText.getContent(false).length)
            imageView.isActivated = !isActivated
        }
    }

    private fun setRegisterText() {
        binding.tvRegister.movementMethod = LinkMovementMethod.getInstance()
        val color = ContextCompat.getColor(this, R.color.colorPrimary)
        binding.tvRegister.text = SpanUtils().append(if (isRegisterView) "我已有账号! " else "还没有账号? ")
            .append(if (isRegisterView) "返回登录" else "立即注册")
            .setForegroundColor(color)
            .setClickSpan(color, true) {
                registerSpanTextClick()
            }.create()
    }

    private fun registerSpanTextClick() {
        if (isRegisterView) {
            binding.motionLayout.transitionToStart()
            binding.btLogin.text = "登录"
            binding.tvWelcome.text = "欢迎登录"
        } else {
            binding.motionLayout.transitionToEnd()
            binding.btLogin.text = "注册"
            binding.tvWelcome.text = "欢迎注册"
        }
        isRegisterView = !isRegisterView
        setRegisterText()
    }


}