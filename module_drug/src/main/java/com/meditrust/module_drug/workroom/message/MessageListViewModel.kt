package com.meditrust.module_drug.workroom.message

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseListViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.model.MsgModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/11/1
 * @desc:
 */
class MessageListViewModel(application: Application) : BaseListViewModel<MsgModel>(application) {

    val msgLiveData: MutableLiveData<ArrayList<MsgModel>> = MutableLiveData()
    val updateMsgLiveData: MutableLiveData<Boolean> = MutableLiveData()

    override fun loadData(page: Int, onResponse: (ArrayList<MsgModel>?) -> Unit) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("page", page)
            jsonObject.put("pageSize", Const.PAGE_SIZE)
            jsonObject.put("sender", Const.SENDER)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())

        ApiService.queryMsgList(requestBody)
            .io_main()
            .subscribeBy({
                msgLiveData.postValue(it?.results)
                onResponse(it?.results)
            }, {
                ToastUtils.showToast(it)
            })
            .add()

    }

    fun updateMsg(msgList: ArrayList<MsgModel>) {
        val jsonObject = JSONObject()
        val jsonArray = JSONArray()
        try {
            for (msg in msgList) {
                jsonArray.put(msg.id)
            }
            jsonObject.put("ids", jsonArray)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())

        ApiService.updateMsg(requestBody)
            .io_main()
            .subscribeBy({
                updateMsgLiveData.value = true
            }, {
                updateMsgLiveData.value = false
                ToastUtils.showToast(it)
            })
            .add()
    }
}