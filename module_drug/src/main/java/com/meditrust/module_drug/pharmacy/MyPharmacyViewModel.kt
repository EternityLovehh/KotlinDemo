package com.meditrust.module_drug.pharmacy

import android.app.Application
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseListViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.common.OrderStatus
import com.meditrust.module_drug.drug.AllDrugActivity
import com.meditrust.module_drug.model.OrderFootModel
import com.meditrust.module_drug.order.OrderActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc:
 */
class MyPharmacyViewModel : BaseListViewModel<BaseItem> {

    val pageStatus: MutableLiveData<String> = MutableLiveData()
    private val context: Context
    var mOrderNo: String? = null
    var mOrderType: String? = null
    var mPatienName: String? = null

    constructor(application: Application) : super(application) {
        context = application.applicationContext
    }

    override fun loadData(page: Int, onResponse: (ArrayList<BaseItem>?) -> Unit) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("orderNo", mOrderNo)
            jsonObject.put("orderType", mOrderType)
            jsonObject.put("page", page)
            jsonObject.put("pageSize", Const.PAGE_SIZE)
            jsonObject.put("patientName", mPatienName)
            jsonObject.put("status", OrderStatus.UN_AUDIT_ORDER)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.queryAllOrder(requestBody)
            .io_main()
            .subscribeBy(
                {
                    val resultsBeanList = it?.results
                val multiList = ArrayList<BaseItem>()
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
                    onResponse(multiList)
                },
                {
                    onResponse(null)
            })
            .add()

    }

    /**
     * 获取用户信息
     */
    fun getPMInfo() {
        ApiService.getPMInfo()
            .io_main()
            .subscribeBy({
                pageStatus?.postValue(it?.userStatus)
            }, {
                ToastUtils.showToast(it)
            }).add()
    }


    fun startDrug(view: View) {
        val intent = Intent(context, AllDrugActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun startOrder(view: View) {
        val intent = Intent(context, OrderActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }
}