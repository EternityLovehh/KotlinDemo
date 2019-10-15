package com.meditrust.module_drug.fragment

import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseFragment
import com.meditrust.module_drug.R
import com.meditrust.module_drug.databinding.FragmentWelfareBinding
import com.meditrust.module_drug.viewmodel.WelfareViewModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class WelfareFragment : BaseFragment<FragmentWelfareBinding, WelfareViewModel>() {
    override fun initView(): Int {
        return R.layout.fragment_welfare
    }

    override fun initData() {

    }

    override fun initViewModel(): WelfareViewModel {
        return WelfareViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }


}