package com.meditrust.module_drug.fragment

import android.view.View
import androidx.lifecycle.Observer
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseFragment
import com.meditrust.module_base.constant.Const
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.OrderAdapter
import com.meditrust.module_drug.databinding.FragmentMyPharmacyBinding
import com.meditrust.module_drug.viewmodel.MyPharmacyViewModel
import kotlinx.android.synthetic.main.layout_unbind_pharmacy.*

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class MyPharmacyFragment : BaseFragment<FragmentMyPharmacyBinding, MyPharmacyViewModel>() {

    var mOrderAdapter: OrderAdapter? = null

    override fun initView(): Int {
        return R.layout.fragment_my_pharmacy
    }

    override fun initData() {
        mViewModel.getPMInfo()

        mViewModel.pageStatus?.observe(this, Observer {
            when (it) {
                Const.UN_BIND_PM -> {
                    mBinding.include.visibility = View.VISIBLE
                    tv_tips.text = getString(R.string.unbind_pharmacy)
                    tv_bind_pharmacy.text = getString(R.string.binding_pharmacy)
                }
                Const.HAVE_BIND_PM -> {
                    mBinding.include.visibility = View.GONE
                    mViewModel.sourceData?.observe(this, Observer {
                        mOrderAdapter.submitList(it)
                    })
                }
                Const.UN_AUDIT_PM -> {
                    mBinding.include.visibility = View.VISIBLE
                    tv_tips.text = getString(R.string.bind_pharmacy_audit)
                    tv_bind_pharmacy.text = getString(R.string.call_server)
                }
                Const.REFUSED_PM -> {
                    mBinding.include.visibility = View.VISIBLE
                    tv_tips.text = getString(R.string.bind_pharmacy_refused)
                    tv_bind_pharmacy.text = getString(R.string.call_server)
                }
            }
        })
    }

    override fun initViewModel(): MyPharmacyViewModel {
        return MyPharmacyViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

}