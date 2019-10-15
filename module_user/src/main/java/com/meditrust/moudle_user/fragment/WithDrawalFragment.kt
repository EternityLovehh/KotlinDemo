package com.meditrust.moudle_user.fragment

import com.meditrust.module_base.base.BaseFragment
import com.meditrust.moudle_user.BR
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.databinding.FragmentWithdrawalBinding
import com.meditrust.moudle_user.viewmodel.WithDrawalViewModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class WithDrawalFragment : BaseFragment<FragmentWithdrawalBinding, WithDrawalViewModel>() {
    override fun initView(): Int {
        return R.layout.fragment_withdrawal
    }

    override fun initData() {
    }

    override fun initViewModel(): WithDrawalViewModel {
        return WithDrawalViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

}