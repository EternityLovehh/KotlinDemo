package com.meditrust.module_drug.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.basebinding.BindingMultiEntity
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.common.OrderStatus
import com.meditrust.module_drug.model.OrderFootModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc:
 */
class MyPharmacyViewModel(application: Application) : BaseViewModel(application) {

    val pageStatus: MutableLiveData<String>? = null

    fun getPMInfo() {
        ApiService.getPMInfo()
            .io_main()
            .subscribeBy({
                pageStatus?.postValue(it?.userStatus)
            }, {
                ToastUtils.showToast(it)
            }).add()
    }

    fun queryOrderList(pageNum: Int) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("orderNo", null)
            jsonObject.put("orderType", null)
            jsonObject.put("page", 1)
            jsonObject.put("pageSize", Const.PAGE_SIZE)
            jsonObject.put("patientName", null)
            jsonObject.put("status", OrderStatus.UN_AUDIT_ORDER)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.queryAllOrder(requestBody)
            .io_main()
            .subscribe({
                val resultsBeanList = it.result.results
                val multiList = ArrayList<BindingMultiEntity>()
                val totalPage = it.result.totalPage
                resultsBeanList?.forEach { it1 ->
                    multiList.add(it1)
                    multiList.addAll(it1.homedeliveryOrderDetailResp)
                    val orderFootModel = OrderFootModel()
                    orderFootModel.actualAmount = it1.actualAmount
                    orderFootModel.status = it1.status
                    orderFootModel.resultsBean = it1
                    orderFootModel.homedeliveryOrderDetailResp = it1.homedeliveryOrderDetailResp
                    multiList.add(orderFootModel)

                }
            }, {
                ToastUtils.showToast(it.message!!)
            })
            .add()

    }

}