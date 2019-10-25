package com.meditrust.module_drug.order

import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jeremyliao.liveeventbus.LiveEventBus
import com.meditrust.module_base.adapter.CommonPageAdapter
import com.meditrust.module_base.base.BaseListActivity
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.common.OrderStatus
import com.meditrust.module_drug.databinding.ActivityOrderBinding
import com.meditrust.module_drug.order.fragment.*
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseListActivity<BaseItem, ActivityOrderBinding, OrderViewModel>() {

    var mTitleList: MutableList<String> = mutableListOf("全部", "待审核", "待发货", "已发货", "已完成")
    var mFragmentList: MutableList<Fragment> = mutableListOf(
        AllOrderFragment(), UnAuditOrderFragment(),
        UnDeliveryOrderFragment(), DeliveredOrderFragment(), CompletedOrderFragment()
    )

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_order
    }

    override fun initData() {
        setToolBar(mBinding.include.toolbar, mBinding.include.tvTitle, "全部订单")
        vp_order.adapter = CommonPageAdapter(this.supportFragmentManager, mFragmentList, mTitleList)
        tl_order.setupWithViewPager(vp_order)
        vp_order.offscreenPageLimit = 5

        mBinding.checkButton.rbHotDrug.isChecked = true
        mBinding.checkButton.rgDrug.setOnCheckedChangeListener { radioGroup: RadioGroup, checkId: Int ->
            if (mBinding.checkButton.rbAllDrug.id == checkId) {
                LiveEventBus.get("order_type").post(Const.COME_STORE)
            } else {
                LiveEventBus.get("order_type").post(Const.SEND_DRUG_HOME)
            }
        }

        tl_order.addOnTabSelectedListener(object :
            TabLayout.ViewPagerOnTabSelectedListener(vp_order) {
            override fun onTabSelected(tab: TabLayout.Tab) {
                super.onTabSelected(tab)
                changeStatus(tab.position)
            }
        })

        vp_order.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {
                changeStatus(position)
            }
        })
    }

    fun changeStatus(position: Int) {
        when (position) {
            0 -> mViewModel.mOrderStatus = null
            1 -> mViewModel.mOrderStatus = OrderStatus.UN_AUDIT_ORDER
            2 -> mViewModel.mOrderStatus =
                OrderStatus.UN_DELIVERY_ORDER + "," + OrderStatus.UN_DELIVERY_AND_PAY
            3 -> mViewModel.mOrderStatus =
                OrderStatus.HAVE_DELIVERY_ORDER + "," + OrderStatus.HAVE_DELIVERY_AND_PAY
            4 -> mViewModel.mOrderStatus = OrderStatus.ORDER_SUCCESS
        }
        LiveEventBus.get("order_status")
            .post(mViewModel.mOrderStatus)
    }

    override fun initViewModel(): OrderViewModel {
        return OrderViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }


}
