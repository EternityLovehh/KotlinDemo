package com.meditrust.module_drug.order.fragment

import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseListFragment
import com.meditrust.module_base.extensions.init
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.OrderAdapter
import com.meditrust.module_drug.databinding.FragmentOrderListBinding
import com.meditrust.module_drug.extensions.component
import com.meditrust.module_drug.order.OrderViewModel
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class UnAuditOrderFragment :
    BaseListFragment<BaseItem, FragmentOrderListBinding, OrderViewModel>() {

    @Inject
    lateinit var mOrderAdapter: OrderAdapter

    override fun initView(): Int {
        return R.layout.fragment_order_list
    }

    override fun initData() {

        mBinding.rlAllOrder.init(mOrderAdapter)

        mViewModel.observeDataObserver(this,
            { mOrderAdapter?.submitList(it) },
            { refreshFinished(it, mBinding.srlAllOrder, mBinding.emptyView) },
            { loadMoreFinished(it) })

        mBinding.srlAllOrder.setOnRefreshListener { mViewModel.invalidate() }

        LiveEventBus.get("order_type")
            .observe(this, Observer {
                mViewModel.mOrderType = it.toString()
                mViewModel.invalidate()
            })

        LiveEventBus.get("order_status")
            .observe(this, Observer {
                mViewModel.mOrderStatus = it?.toString()
                mViewModel.invalidate()
            })
    }

    override fun initViewModel(): OrderViewModel {
        return OrderViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initInject() {
        component().inject(this)
    }

}