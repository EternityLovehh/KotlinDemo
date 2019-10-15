package com.meditrust.moudle_user.ui

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.databinding.ActivitySetPwdBinding
import com.meditrust.moudle_user.viewmodel.SetPwdViewModel

@Route(path = "/user/SetPwdActivity")
class SetPwdActivity : BaseActivity<ActivitySetPwdBinding, SetPwdViewModel>() {
    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_set_pwd
    }

    override fun initData() {
        setSupportActionBar(mBinding.include.toolbar)
        mBinding.include.tvTitle.text = "登录"
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initViewModel(): SetPwdViewModel {
        return SetPwdViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }


    fun savePwd(view: View) {
        mViewModel.setPwd(mBinding.etPassword.text.trim().toString(), mBinding.etAgainPassword.text.trim().toString())
    }

}
