package com.meditrust.module_drug.workroom.message

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.constant.RefreshResult
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.model.MsgInfoModel
import io.reactivex.rxkotlin.subscribeBy

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/31
 * @desc:
 */
class MessageViewModel(application: Application) : BaseViewModel(application) {

    val loadLiveData: MutableLiveData<RefreshResult> = MutableLiveData()
    val mMsgLiveData: MutableLiveData<MsgInfoModel> = MutableLiveData()
    val context: Context = application.applicationContext

    fun queryMsg() {
        ApiService.queryMsgInfo(Const.SENDER)
            .io_main()
            .subscribeBy {
                mMsgLiveData.postValue(it.result)
                when {
                    it == null || it.success.not() -> loadLiveData.value = RefreshResult.FAILED
                    it.success -> loadLiveData.value = RefreshResult.SUCCEED
                    it.result == null -> loadLiveData.value = RefreshResult.NO_DATA
                }
            }.add()
    }
}