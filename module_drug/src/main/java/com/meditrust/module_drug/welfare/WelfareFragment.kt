package com.meditrust.module_drug.welfare

import com.meditrust.module_base.base.BaseListFragment
import com.meditrust.module_base.extensions.init
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.WelfareAdapter
import com.meditrust.module_drug.databinding.FragmentWelfareBinding
import com.meditrust.module_drug.extensions.component
import com.meditrust.module_drug.model.RecruitModel
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class WelfareFragment : BaseListFragment<RecruitModel, FragmentWelfareBinding, WelfareViewModel>() {

    @Inject
    lateinit var mWelflareAdapter: WelfareAdapter

    override fun initView(): Int {
        return R.layout.fragment_welfare
    }

    override fun initData() {
        mBinding.rlWelfare.init(mWelflareAdapter)

        mViewModel.observeDataObserver(this,
            { mWelflareAdapter.submitList(it) },
            { refreshFinished(it, null, mBinding.emptyView) },
            { loadMoreFinished(it) })

    }

    override fun onResume() {
        super.onResume()
        mViewModel.mRecruitType?.value = null
    }

    override fun initViewModel(): WelfareViewModel {
        return WelfareViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initInject() {
        component().inject(this)
    }


}