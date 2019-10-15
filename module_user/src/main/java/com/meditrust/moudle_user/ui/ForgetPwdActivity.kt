package com.meditrust.moudle_user.ui

import android.text.InputType
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.databinding.ActivityForgetPwdBinding
import com.meditrust.moudle_user.viewmodel.ForgetPwdViewModel

class ForgetPwdActivity : BaseActivity<ActivityForgetPwdBinding, ForgetPwdViewModel>() {
    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_forget_pwd
    }

    override fun initData() {
        setSupportActionBar(mBinding.include.toolbar)
        mBinding.include.tvTitle.text = "忘记密码"
        supportActionBar?.setDisplayShowTitleEnabled(false)
        mBinding.include.toolbar.setNavigationOnClickListener { finish() }
        mBinding.include.toolbar.navigationIcon = getDrawable(R.drawable.icon_black_back)

        checkChange(mBinding.cbPasswordShow, mBinding.etPassword)
        checkChange(mBinding.cbForgetPasswordShow, mBinding.etAgainPassword)

    }

    override fun initViewModel(): ForgetPwdViewModel {
        return ForgetPwdViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    private fun checkChange(checkBox: CheckBox, editText: EditText) {
        checkBox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                if (isChecked) {
                    //选择状态 显示明文--设置为可见的密码
                    editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    editText.setSelection(editText.text.toString().length)
                } else run {
                    //默认状态显示密码--设置文本 要一起写才能起作用
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    editText.setSelection(editText.text.toString().length)
                }
            }
        })
    }

    /**
     * 获取验证码
     */
    fun getCode(view: View) {
        mViewModel.getCodeTime(mBinding.etCode.text.toString())
    }

    fun editPwd(view: View) {
        mViewModel.retrievePwd(
            mBinding.etPhone.text.trim().toString(), mBinding.etCode.text.trim().toString(),
            mBinding.etPassword.text.trim().toString(), mBinding.etAgainPassword.text.trim().toString()
        )
    }
}
