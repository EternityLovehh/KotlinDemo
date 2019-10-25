package com.meditrust.module_drug.upload

import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.databinding.ActivityUploadIdCardBinding

class UploadIdCardActivity : BaseActivity<ActivityUploadIdCardBinding, UploadIdCardViewModel>() {
    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_upload_id_card
    }

    override fun initData() {
    }

    override fun initViewModel(): UploadIdCardViewModel {
        return UploadIdCardViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

}
