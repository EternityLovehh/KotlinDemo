package com.meditrust.moudle_user.ui

import android.text.InputType
import android.view.View
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_base.constant.Const
import com.meditrust.moudle_user.BR
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.databinding.ActivityLoginBinding
import com.meditrust.moudle_user.viewmodel.LoginViewModel

@Route(path = "/user/LoginActivity")
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_login
    }

    override fun initData() {
        setSupportActionBar(mBinding.include.toolbar)
        mBinding.include.tvTitle.text = "登录"
        supportActionBar?.setDisplayShowTitleEnabled(false)

        mViewModel.mPwdVisible.observe(this, Observer<Boolean> {
            if (it) {
                //选择状态 显示明文--设置为可见的密码
                mBinding.llPwdEdit.etPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                mBinding.llPwdEdit.etPassword.setSelection(mBinding.llPwdEdit.etPassword.text.toString().length)
            } else run {
                //默认状态显示密码--设置文本 要一起写才能起作用
                mBinding.llPwdEdit.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                mBinding.llPwdEdit.etPassword.setSelection(mBinding.llPwdEdit.etPassword.text.toString().length)
            }
        })

    }

    override fun initViewModel(): LoginViewModel {
        return LoginViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    /**
     * 获取验证码
     */
    fun getCode(view: View) {
        mViewModel.getCodeTime(mBinding.llCodeEdit.etPhoneSms.text.toString())
    }

    /**
     * 验证码登录
     */
    fun loginCode(view: View) {
        mViewModel.isRegistered(
            Const.SEND_DRUG_HOME,
            mBinding.llCodeEdit.etPhoneSms.text.toString(),
            mBinding.llCodeEdit.etCode.text.toString(),
            null
        )
    }

    /**
     * 密码登录
     */
    fun loginPwd(view: View) {
        mViewModel.isRegistered(
            Const.COME_STORE,
            mBinding.llPwdEdit.etPhone.text.toString(),
            null,
            mBinding.llPwdEdit.etPassword.text.toString()
        )
    }

    fun switchPwd(view: View) {
        mBinding.llCodeEdit.llSmscode.visibility = View.GONE
        mBinding.llPwdEdit.llPassword.visibility = View.VISIBLE
    }

    fun switchCode(view: View) {
        mBinding.llCodeEdit.llSmscode.visibility = View.VISIBLE
        mBinding.llPwdEdit.llPassword.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.disposable()
    }

}
