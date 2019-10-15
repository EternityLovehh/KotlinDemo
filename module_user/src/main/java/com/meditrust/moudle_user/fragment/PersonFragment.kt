package com.meditrust.moudle_user.fragment

import com.meditrust.module_base.base.BaseFragment
import com.meditrust.moudle_user.BR
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.databinding.FragmentPersonBinding
import com.meditrust.moudle_user.viewmodel.PersionViewModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class PersonFragment : BaseFragment<FragmentPersonBinding, PersionViewModel>() {
    override fun initView(): Int {
        return R.layout.fragment_person
    }

    override fun initData() {
    }

    override fun initViewModel(): PersionViewModel {
        return PersionViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

}