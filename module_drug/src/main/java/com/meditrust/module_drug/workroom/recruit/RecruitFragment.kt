package com.meditrust.module_drug.workroom.recruit

import com.meditrust.module_base.base.BaseListFragment
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.init
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.WelfareAdapter
import com.meditrust.module_drug.databinding.FragmentRecruitBinding
import com.meditrust.module_drug.extensions.component
import com.meditrust.module_drug.model.RecruitModel
import com.meditrust.module_drug.order.OrderActivity
import com.meditrust.module_drug.welfare.WelfareViewModel
import org.jetbrains.anko.support.v4.startActivity
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/28
 * @desc:
 */
class RecruitFragment : BaseListFragment<RecruitModel, FragmentRecruitBinding, WelfareViewModel>() {

    @Inject
    lateinit var mWelflareAdapter: WelfareAdapter

    override fun initView(): Int {
        return R.layout.fragment_recruit
    }

    override fun initData() {
        mViewModel.mRecruitType?.value = Const.DRUG_RECRUIT
        mBinding.rlRecruit.init(mWelflareAdapter)

        mViewModel.observeDataObserver(this,
            { mWelflareAdapter.submitList(it) },
            { refreshFinished(it, null, mBinding.emptyView) },
            { loadMoreFinished(it) })

        mWelflareAdapter.itemClickListener = { view, item, position ->
            startActivity<OrderActivity>(
                Pair(Const.RECRUIT_TITLE, item.title),
                Pair(Const.RECRUIT_ID, item.id),
                Pair(Const.RECRUIT_TYPE, mViewModel.mRecruitType?.value)
            )
        }

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