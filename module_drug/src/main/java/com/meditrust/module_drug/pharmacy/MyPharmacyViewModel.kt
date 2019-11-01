package com.meditrust.module_drug.pharmacy

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jeremyliao.liveeventbus.LiveEventBus
import com.meditrust.module_base.base.BaseListViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.constant.Urls
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_base.utils.DialogUtils
import com.meditrust.module_base.utils.SpUtils
import com.meditrust.module_base.utils.SpUtils.getString
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_base.utils.WebUtils
import com.meditrust.module_drug.R
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.common.OrderStatus
import com.meditrust.module_drug.drug.AllDrugActivity
import com.meditrust.module_drug.model.*
import com.meditrust.module_drug.order.OrderActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.jetbrains.anko.startActivity
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Singleton

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc:
 */
class MyPharmacyViewModel : BaseListViewModel<BaseItem> {

    var pageStatus: MutableLiveData<String> = MutableLiveData()
    val mIntegtalLiveData: MutableLiveData<IntegtalModel> = MutableLiveData()
    val mPharmacyLiveData: MutableLiveData<PharmacyModel> = MutableLiveData()
    val mPharmacyDetailLiveData: MutableLiveData<PharmacyDetailModel> = MutableLiveData()
    val mWeChatInfoLiveData: MutableLiveData<WeChatInfoModel> = MutableLiveData()
    val mRulesLiveData: MutableLiveData<List<ProtocolModel>> = MutableLiveData()
    val mQulifeLiveData: MutableLiveData<WithDrawalModel> = MutableLiveData()
    private val context: Context
    var mOrderNo: String? = null
    var mOrderType: String? = null
    var mPatienName: String? = null

    @Singleton
    constructor(application: Application) : super(application) {
        context = application.applicationContext
    }

    override fun loadData(page: Int, onResponse: (ArrayList<BaseItem>?) -> Unit) {
        val jsonObject = JSONObject()
        try {
            jsonObject.apply {
                put("orderNo", mOrderNo)
                put("orderType", mOrderType)
                put("page", page)
                put("pageSize", Const.PAGE_SIZE)
                put("patientName", mPatienName)
                put("status", OrderStatus.UN_AUDIT_ORDER)
            }

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
                mPharmacyLiveData.postValue(it)
                SpUtils.putString("bind_status", it!!.userStatus)
                LiveEventBus.get("pharmacy_info").post(it)
                if (TextUtils.equals(it?.remark1, "*")) {
                    updateInfo()
                }
                queryWechatInfo()

                SpUtils.putString(SpUtils.SP_KEYS.USER_ID, it!!.userId)
                SpUtils.putString(SpUtils.SP_KEYS.USER_NAME, it!!.userName)
                SpUtils.putString(SpUtils.SP_KEYS.USER_PHONE, it!!.phoneNo)
                SpUtils.putString(SpUtils.SP_KEYS.QR_VALUE, it!!.qrValue)
            }, {
                ToastUtils.showToast(it)
            }).add()
    }

    private fun updateInfo() {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("remark1", "Y")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
    }

    /**
     * 查询积分信息
     */
    fun queryIntegral() {
        ApiService.queryIntegral()
            .io_main()
            .subscribeBy({
                mIntegtalLiveData.postValue(it)
                LiveEventBus.get("integtal_info").post(it)
            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

    /**
     * 查询积分规则
     */
    fun queryRules() {
        ApiService.getProtocol(Const.WITH_DRAW_RULES_CODE)
            .io_main()
            .subscribeBy({
                mRulesLiveData.postValue(it)
            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

    /**
     * 查询提现资格
     */
    fun qulifeDrawal() {
        ApiService.queryQulife()
            .io_main()
            .subscribeBy({
                mQulifeLiveData.postValue(it)

            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

    /**
     * 查询药房信息
     */
    fun queryPharmacyForId(pharmacyId: Long) {
        ApiService.queryPharmacyForId(pharmacyId)
            .io_main()
            .subscribeBy({
                mPharmacyDetailLiveData.postValue(it)
            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

    /**
     * 查询微信信息
     */
    fun queryWechatInfo() {
        ApiService.queryWechatInfo()
            .io_main()
            .subscribeBy({
                mWeChatInfoLiveData.postValue(it)
                LiveEventBus.get("wechat_info").post(it)
            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

    fun startDrug(view: View) {
        context.startActivity<AllDrugActivity>()
    }

    fun startOrder(view: View) {
        context.startActivity<OrderActivity>()
    }

    fun startIntegtal(view: View) {
        WebUtils.startWeb(
            context,
            Urls.INTEGRAL_HISTORY,
            context.getString(R.string.integral_record)
        )
    }

    fun startIntegtalRank(view: View) {
        WebUtils.startWeb(context, Urls.INTEGRAL_RANK, context.getString(R.string.integral_rank))
    }

    fun startOrderReceipt(view: View) {
        WebUtils.startWeb(context, Urls.ORDER_RECIPT, context.getString(R.string.order_reconce))
    }

    fun startSetting(view: View) {
        context.startActivity<OrderActivity>()
    }

    fun startAnyPages(view: View, type: Int) {
        when (getString("bind_status")) {
            Const.UN_BIND_PM -> DialogUtils.showBindDialog(
                context,
                context.getString(R.string.unbind_pharmacy)
            )
            Const.UN_AUDIT_PM -> ToastUtils.showToast(context.getString(R.string.auditing_pharmacy))
            Const.REFUSED_PM -> DialogUtils.showBindDialog(
                context,
                context.getString(R.string.refused_tips)
            )
            Const.HAVE_BIND_PM -> {
                when (type) {
                    1 -> { //详情
                        val intent = Intent(context, OrderActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        context.startActivity(intent)
                    }
                    2 -> { //成员
                        val intent = Intent(context, OrderActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.putExtra(
                            Const.PHARMACY_ID,
                            mPharmacyDetailLiveData.value?.pharmacyId
                        )
                        intent.putExtra(Const.USER_ID, mPharmacyLiveData.value?.userId)
                        context.startActivity(intent)
                    }
                    3 -> {
                        WebUtils.startWeb(
                            context,
                            Urls.MY_WELFARE,
                            context.getString(R.string.my_welfare)
                        )
                    }

                }
            }
        }

    }

}