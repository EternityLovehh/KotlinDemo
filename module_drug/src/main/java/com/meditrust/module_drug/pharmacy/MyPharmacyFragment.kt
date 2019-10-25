package com.meditrust.module_drug.pharmacy

import androidx.lifecycle.Observer
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseListFragment
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.gone
import com.meditrust.module_base.extensions.init
import com.meditrust.module_base.extensions.visible
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.OrderAdapter
import com.meditrust.module_drug.databinding.FragmentMyPharmacyBinding
import com.meditrust.module_drug.extensions.component
import kotlinx.android.synthetic.main.layout_unbind_pharmacy.*
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class MyPharmacyFragment :
    BaseListFragment<BaseItem, FragmentMyPharmacyBinding, MyPharmacyViewModel>() {

    @Inject
    lateinit var mOrderAdapter: OrderAdapter

    override fun initView(): Int {
        return R.layout.fragment_my_pharmacy
    }

    override fun initData() {

        mBinding.rlAuditOrder.init(mOrderAdapter)

        mViewModel.getPMInfo()

        mViewModel.pageStatus?.observe(this, Observer { it1 ->
            when (it1) {
                Const.UN_BIND_PM -> {
                    mBinding.include.llUnbindPharmacy.visible()
                    mBinding.emptyView.gone()
                    tv_tips.text = getString(R.string.unbind_pharmacy)
                    tv_bind_pharmacy.text = getString(R.string.binding_pharmacy)
                }
                Const.HAVE_BIND_PM -> {
                    mBinding.include.llUnbindPharmacy.gone()
                    mBinding.emptyView.visible()
                    mViewModel.observeDataObserver(this,
                        { mOrderAdapter?.submitList(it) },
                        { refreshFinished(it, mBinding.srlNoAuditOrder, mBinding.emptyView) },
                        { loadMoreFinished(it) })
                }
                Const.UN_AUDIT_PM -> {
                    mBinding.include.llUnbindPharmacy.visible()
                    mBinding.emptyView.gone()
                    tv_tips.text = getString(R.string.bind_pharmacy_audit)
                    tv_bind_pharmacy.text = getString(R.string.call_server)
                }
                Const.REFUSED_PM -> {
                    mBinding.include.llUnbindPharmacy.visible()
                    mBinding.emptyView.gone()
                    tv_tips.text = getString(R.string.bind_pharmacy_refused)
                    tv_bind_pharmacy.text = getString(R.string.call_server)
                }
            }
        })

        mBinding.srlNoAuditOrder.setOnRefreshListener { mViewModel.refresh() }
    }

    override fun initViewModel(): MyPharmacyViewModel {
        return MyPharmacyViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initInject() {
        component().inject(this)
    }
}