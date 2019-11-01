package com.meditrust.module_drug.person

import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.meditrust.module_base.base.BaseFragment
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.utils.ImageLoadUtils
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.databinding.FragmentPersonBinding
import com.meditrust.module_drug.model.IntegtalModel
import com.meditrust.module_drug.model.PharmacyModel
import com.meditrust.module_drug.model.WeChatInfoModel
import com.meditrust.module_drug.pharmacy.MyPharmacyViewModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class PersonFragment : BaseFragment<FragmentPersonBinding, MyPharmacyViewModel>() {
    override fun initView(): Int {
        return R.layout.fragment_person
    }

    override fun initData() {

        LiveEventBus.get("pharmacy_info").observeSticky(this, Observer {
            if (it is PharmacyModel) {
                mBinding.pharmacy = it

                if (it.userStatus == Const.HAVE_BIND_PM) {
                    mViewModel.queryPharmacyForId(it.pharmacyId)
                }
            }
        })

        LiveEventBus.get("integtal_info").observeSticky(this, Observer {
            if (it is IntegtalModel)
                mBinding.integtal = it
        })

        LiveEventBus.get("wechat_info").observeSticky(this, Observer {
            if (it is WeChatInfoModel)
                it.headImgUrl?.let { it1 -> ImageLoadUtils.loadCircle(it1, mBinding.ivPortrait) }
        })

        mViewModel.mPharmacyDetailLiveData.observe(this, Observer {
            mBinding.pharmacydetail = it
        })
    }

    override fun initViewModel(): MyPharmacyViewModel {
        return MyPharmacyViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

}