package com.meditrust.moudle_user.ui

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.databinding.ActivityNickNameBinding
import com.meditrust.moudle_user.viewmodel.NickNameViewModel

@Route(path = "/user/NickNameActivity")
class NickNameActivity : BaseActivity<ActivityNickNameBinding, NickNameViewModel>() {

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_nick_name
    }

    override fun initData() {
        setSupportActionBar(mBinding.include.toolbar)
        mBinding.include.tvTitle.text = "登录"
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun initViewModel(): NickNameViewModel {
        return NickNameViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    fun saveNickName(view: View) {
        mViewModel.setNickName(mBinding.etNickName.text.trim().toString())
    }

}
