package com.meditrust.module_drug.order

import android.app.Application
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseListViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.model.OrderFootModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/22
 * @desc:
 */
class OrderViewModel(application: Application) : BaseListViewModel<BaseItem>(application) {

    var mOrderType: String = Const.SEND_DRUG_HOME
    var mOrderStatus: String? = null
    val remarkLiveData: MutableLiveData<String> = MutableLiveData()
    val remarkVisible: MutableLiveData<Boolean> = MutableLiveData()

    val mTabSendDrug: MutableLiveData<String> = MutableLiveData()
    val mTabComeStore: MutableLiveData<String> = MutableLiveData()


    init {
        mTabSendDrug.value = "送药到家"
        mTabComeStore.value = "到店自取"
    }

    override fun loadData(page: Int, onResponse: (ArrayList<BaseItem>?) -> Unit) {
        Log.e("tag", "$mOrderType---$mOrderStatus")
        val jsonObject = JSONObject()
        try {
            jsonObject.put("orderNo", null)
            jsonObject.put("orderType", mOrderType)
            jsonObject.put("page", page)
            jsonObject.put("pageSize", Const.PAGE_SIZE)
            jsonObject.put("patientName", null)
            jsonObject.put("status", mOrderStatus)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.queryAllOrder(requestBody)
            .io_main()
            .subscribeBy({
                val resultsBeanList = it?.results
                val multiList = ArrayList<BaseItem>()
                resultsBeanList?.forEach { it1 ->
                    it1.remark2?.let { it2 -> getRemark(it2) }

                    multiList.add(it1)
                    multiList.addAll(it1.homedeliveryOrderDetailResp)
                    val orderFootModel = OrderFootModel()
                    orderFootModel.actualAmount = it1.actualAmount
                    orderFootModel.status = it1.status
                    orderFootModel.resultsBean = it1
                    orderFootModel.homedeliveryOrderDetailResp = it1.homedeliveryOrderDetailResp
                    multiList.add(orderFootModel)
                }
                onResponse(multiList)
            },
                {
                    onResponse(null)
                })
            .add()
    }

    private fun getRemark(remark: String) {
        if (TextUtils.isEmpty(remark)) {
            remarkVisible.value = false
        } else {
            remarkVisible.value = true
            when (remark) {
                Const.SEND_DRUG_HOME -> remarkLiveData.value = "特药"
                Const.COME_STORE -> remarkLiveData.value = "分期"
                else -> remarkLiveData.value = "慢病"
            }

        }
    }


}