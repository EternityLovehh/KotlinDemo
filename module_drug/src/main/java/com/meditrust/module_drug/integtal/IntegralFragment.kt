package com.meditrust.module_drug.integtal

import android.view.View
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.meditrust.module_base.adapter.BaseBindingAdapter
import com.meditrust.module_base.base.BaseFragment
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.constant.Urls
import com.meditrust.module_base.dialog.BindViewHolder
import com.meditrust.module_base.dialog.CommonDialog
import com.meditrust.module_base.extensions.init
import com.meditrust.module_base.listener.OnDialogBindViewListener
import com.meditrust.module_base.listener.OnDialogViewClickListener
import com.meditrust.module_base.utils.DialogUtils
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_base.utils.WebUtils
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.databinding.FragmentWithdrawalBinding
import com.meditrust.module_drug.model.PharmacyModel
import com.meditrust.module_drug.model.ProtocolModel
import com.meditrust.module_drug.pharmacy.MyPharmacyViewModel
import kotlinx.android.synthetic.main.fragment_withdrawal.*

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class IntegralFragment : BaseFragment<FragmentWithdrawalBinding, MyPharmacyViewModel>(),
    View.OnClickListener {

    lateinit var mRulesAdapter: BaseBindingAdapter<ProtocolModel>

    override fun initView(): Int {
        return R.layout.fragment_withdrawal
    }

    override fun initData() {

        mViewModel.queryRules()

        mRulesAdapter = BaseBindingAdapter(BR.bean, R.layout.item_with_drawal_rule)
        mBinding.rlWithDrawalRule.init(mRulesAdapter)
        mViewModel.mRulesLiveData.observe(this, Observer {
            mRulesAdapter.setData(it)
        })

        mBinding.llWithDrawalTutorial.setOnClickListener(this)
        mBinding.llIntegralRecord.setOnClickListener(this)
        mBinding.tvIntegralRules.setOnClickListener(this)
        mBinding.tvMoreRules.setOnClickListener(this)
        mBinding.btnWithDrawal.setOnClickListener(this)

        LiveEventBus.get("pharmacy_info").observeSticky(this, Observer {
            if (it is PharmacyModel)
                when (it.userStatus) {
                    Const.HAVE_BIND_PM -> {
                        mViewModel.queryIntegral()
                    }
                }
        })


        mViewModel.mIntegtalLiveData.observe(this, Observer {
            tv_residue_integral.text = it.current.toString()
        })

        mViewModel.mQulifeLiveData.observe(this, Observer {
            when (it?.code) {
                Const.NO_SING_AND_IDCARD -> {
                }
                Const.HAVE_IDCARD -> {
                }
                Const.HAVE_SING, Const.WITH_DRAWAL, Const.HAVE_WITH_DRAWAL -> {
                }
                Const.ACCOUNT_FREEZE, Const.ACCOUNT_LOGOUT,
                Const.HAVE_FREEZE_INTEGRAL, Const.NEGATIVE_POINT
                -> showTipsDialog(it.message)
            }
        })
    }


    override fun initViewModel(): MyPharmacyViewModel {
        return MyPharmacyViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.ll_with_drawal_tutorial -> WebUtils.startWeb(
                activity!!.applicationContext,
                Urls.INTEGRAL_TURORIAL,
                getString(R.string.with_drawal_tutorial)
            )
            R.id.ll_integral_record -> WebUtils.startWeb(
                activity!!.applicationContext,
                Urls.INTEGRAL_HISTORY,
                getString(R.string.integral_record)
            )
            R.id.tv_integral_rules -> WebUtils.startWeb(
                activity!!.applicationContext,
                Urls.INTEGRAL_RULES,
                getString(R.string.integral_rules)
            )
            R.id.tv_more_rules -> WebUtils.startWeb(
                activity!!.applicationContext,
                Urls.MONEY_RULES,
                getString(R.string.with_drawal_rules)
            )
            R.id.btn_with_drawal -> LiveEventBus.get("bind_status").observeSticky(this, Observer {
                when (it) {
                    Const.UN_BIND_PM -> DialogUtils.showBindDialog(
                        activity!!.applicationContext,
                        getString(R.string.unbind_pharmacy)
                    )
                    Const.UN_AUDIT_PM -> ToastUtils.showToast(getString(R.string.auditing_pharmacy))
                    Const.REFUSED_PM -> DialogUtils.showBindDialog(
                        activity!!.applicationContext,
                        getString(R.string.refused_tips)
                    )
                    Const.HAVE_BIND_PM -> mViewModel.qulifeDrawal()
                }
            })
        }
    }

    private fun showTipsDialog(message: String) {
        CommonDialog.Builder(childFragmentManager)
            .setLayoutRes(R.layout.dialog_with_drawal)
            .setScreenWidthAspect(context!!.applicationContext, 0.8f)
            .setScreenHeightAspect(context!!.applicationContext, 0.3f)
            .addOnClickListener(intArrayOf(R.id.tv_confirm))
            .setTag(Const.WITH_DRAWAL_UNUSUAL)
            .setOnBindViewListener(object : OnDialogBindViewListener {
                override fun bindView(viewHolder: BindViewHolder) {
                    viewHolder.setText(
                        R.id.tv_with_drawal_reason,
                        message
                    )
                }

            })
            .setOnViewClickListener(object : OnDialogViewClickListener {
                override fun onDialogViewClick(
                    viewHolder: BindViewHolder,
                    view: View,
                    commonDialog: CommonDialog
                ) {
                    commonDialog.dismiss()
                }

            })
            .create()
            .show()
    }

}