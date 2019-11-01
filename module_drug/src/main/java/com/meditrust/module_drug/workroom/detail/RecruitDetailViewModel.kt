package com.meditrust.module_drug.workroom.detail

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.model.RecruitDetailModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/11/1
 * @desc:
 */
class RecruitDetailViewModel(application: Application) : BaseViewModel(application) {

    val mBottomLiveData: MutableLiveData<Boolean>? = null
    val mRecruitText: MutableLiveData<String>? = null
    val mQuestInfo: MutableLiveData<String>? = null
    val mBottomTypeLiveData: MutableLiveData<Boolean>? = null

    val mRecruitDetailLiveData: MutableLiveData<RecruitDetailModel>? = null

    fun recruitType(recruitType: String) {

        mBottomLiveData?.value = !TextUtils.isEmpty(recruitType)
        when (recruitType) {
            Const.DRUG_RECRUIT -> {
                mRecruitText?.value = "患者扫描二维码参与活动"
                mQuestInfo?.value = "填写已完成招募的患者信息"
                mBottomTypeLiveData?.value = true
            }
            Const.STAMP_RECRYUT -> {
                mRecruitText?.value = "患者扫描二维码填写问卷"
                mQuestInfo?.value = "上传已填写完成的纸质问券照片"
                mBottomTypeLiveData?.value = true
            }
            Const.INSURAN_RECRUIT -> {
                mRecruitText?.value = "分享邀请患者购买获取积分"
                mBottomTypeLiveData?.value = false
            }
        }
    }

    fun queryRecruitDetail(mRecruitId: String) {
        ApiService.queryRecruitDetail(mRecruitId)
            .io_main()
            .subscribeBy({
                mRecruitDetailLiveData?.postValue(it)
            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

    fun addClick(mRecruitId: String) {
        ApiService.addClickNum(mRecruitId)
            .io_main()
            .subscribeBy({

            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }
}