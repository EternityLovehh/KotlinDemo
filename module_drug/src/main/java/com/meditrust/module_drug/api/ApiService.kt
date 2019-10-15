package com.meditrust.module_drug.api

import com.meditrust.module_base.MyApplication
import com.meditrust.module_base.base.BaseModel
import com.meditrust.module_drug.model.OrderMultipleModel
import com.meditrust.module_drug.model.PharmacyModel
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/20
 * @desc: 请求接口
 */

object ApiService : Service by MyApplication.instance.appComponent.retrofit().create(Service::class.java)

interface Service {
    /**
     * 登录请求
     *
     * @param requestBody
     * @return
     */
    @POST("api/v1/miniApp/user/login")
    fun login(@Header("ossType") ossType: String, @Body requestBody: RequestBody): Observable<BaseModel<Any>>

    /**
     * 退出登录
     */
    @GET("api/v1/miniApp/user/logout")
    fun loginOut(): Observable<BaseModel<Void>>

    /**
     * 判断是否已经注册
     *
     * @param phoneNum
     * @return
     */
    @GET("api/v1/user/phoneNo/isRegistered")
    fun isResgistered(@Query("phoneNo") phoneNum: String): Observable<BaseModel<Any>>

    /**
     * 获取手机登录短信验证码
     *
     * @param requestBody
     * @return
     */
    @POST("api/v1/app/user/register/sms")
    fun getSmsCode(@Body requestBody: RequestBody): Observable<BaseModel<Any>>

    /**
     * 找回密码
     *
     * @param requestBody
     * @return
     */
    @POST("api/v1/user/loginPassword/find")
    fun retrievePwd(@Body requestBody: RequestBody): Observable<BaseModel<Void>>

    /**
     * 设置密码
     *
     * @param requestBody
     * @return
     */
    @POST("api/v1/app/user/register/loginPassword/set")
    fun savePwd(@Body requestBody: RequestBody): Observable<BaseModel<Void>>

    /**
     * 设置昵称
     *
     * @param requestBody
     * @return
     */
    @POST("api/v1/user/update/info")
    fun saveNickname(@Body requestBody: RequestBody): Observable<BaseModel<Void>>

    /**
     * 获取药师信息
     *
     * @return
     */
    @GET("api/v1/miniApp/user/query")
    fun getPMInfo(): Observable<BaseModel<PharmacyModel>>

    /**
     * 绑定药店申请
     *
     * @param requestBody
     * @return
     */
    @POST("api/v1/user/bindingPharmacy/apply")
    fun bindPharmacy(@Body requestBody: RequestBody): Observable<BaseModel<Void>>

    /**
     * 是否绑定药店
     */
    @GET("api/v1/user/isBingdingPharmacy")
    fun isBindPharmacy(): Observable<BaseModel<Void>>

    /**
     * 获取协议
     */
//    @GET("api/v1/data/{dictCode}")
//    fun getProtocol(@Path("dictCode") dictCode: String): Observable<BaseListModel<ProtocolModel>>
//
//    /**
//     * 药师积分账户 查询
//     */
//    @GET("api/v1/integral/account")
//    fun queryIntegral(): Observable<BaseModel<IntegtalModel>>
//
//    /**
//     * 省市区查询药房
//     */
//    @POST("api/v1/maindata/area/pharmacy/query")
//    fun queryFormCity(@Body requestBody: RequestBody): Observable<BaseModel<PharmacyListModel>>
//
//    /**
//     * 绑定药店申请
//     */
//    @POST("api/v1/user/bindingPharmacy/apply")
//    fun applyBindPharmacy(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 解绑药店
//     */
//    @GET("api/v1/user/unbindingPharmacy")
//    fun unBindPharmacy(@Query("userId") userId: String): Observable<BaseModel<Void>>
//
//    /**
//     * 药店是否有店长
//     */
//    @GET("api/v1/pharmacy/hasManager")
//    fun hasManager(@Query("pharmacyId") pharmacyId: Long): Observable<BooleanModel>
//
//    /**
//     * 根据药店id查询已上架的实体药店信息
//     */
//    @GET("api/v1/maindata/drug/pharmacy/queryPharmacyById")
//    fun queryPharmacyForId(@Query("id") id: Long): Observable<BaseModel<PharmacyDetailModel>>
//
//    /**
//     * 微信绑定信息查询
//     */
//    @GET("api/v1/integral/withdraw/wechat/info")
//    fun queryWechatInfo(): Observable<BaseModel<WeChatInfoModel>>
//
//    /**
//     * 查询药店成员信息
//     */
//    @GET("api/v1/user/members/query")
//    fun queryMembers(): Observable<BaseModel<PharmacyMemberModel>>
//
//    /**
//     * 绑定药店审核通过
//     * @param userId
//     * @return
//     */
//    @GET("api/v1/user/bindingPharmacy/audit/pass")
//    fun auditPass(@Query("userId") userId: String): Observable<BaseModel<Void>>
//
//    /**
//     * 绑定药店审核拒绝
//     */
//    @GET("api/v1/user/bindingPharmacy/audit/unpass")
//    fun unAuditPass(@Query("userId") userId: String): Observable<BaseModel<Void>>
//
//    /**
//     * 查询招募活动
//     */
//    @POST("api/v1/recruitPoint/query")
//    fun queryRecruit(@Body requestBody: RequestBody): Observable<BaseListModel<RecruitModel>>
//
//    /**
//     * 提现资格查询
//     */
//    @GET("api/v1/integral/withdraw/able")
//    fun queryQulife(): Observable<BaseModel<WithDrawalModel>>
//
//    /**
//     * 药师积分提现
//     */
//    @POST("api/v1/integral/withdraw")
//    fun withDrawal(@Header("ossType") ossType: String, @Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 药师提现是否上传了身份证
//     */
//    @GET("api/v1/integral/withdraw/idcard/exist")
//    fun isIdCardExit(): Observable<BaseModel<Void>>
//
//    /**
//     * 上传身份证照片
//     */
//    @POST("api/v1/integral/withdraw/idcard/upload")
//    fun uploadIdCardFile(@Header("ossType") ossType: String, @Body requestBody: RequestBody): Observable<BaseModel<UploadIdCardModel>>
//
//    /**
//     * 地区查询
//     */
//    @GET("api/v1/oss/china")
//    fun queryArea(): Observable<BaseListModel<ProvinceModel>>
//
//    /**
//     * 药品查询
//     */
//    @POST("api/v1/maindata/pharmacist/select/drug")
//    fun queryDrugList(@Body requestBody: RequestBody): Observable<BaseModel<DrugModel>>
//
//    /**
//     * 修改价格和库存
//     */
//    @POST("api/v1/maindata/drug/commodity/update")
//    fun alterDrug(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 上架药品
//     * @param requestBody
//     * @return
//     */
//    @POST("api/v1/maindata/drug/commodity/batch/up")
//    fun upDrug(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 下架药品
//     */
//    @POST("api/v1/maindata/drug/commodity/batch/down")
//    fun downDrug(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 上下架记录
//     */
//    @POST("api/v1/maindata/drug/commodity/change/record/query")
//    fun queryDrugHistory(@Body requestBody: RequestBody): Observable<BaseModel<DrugModel>>

    /**
     * 订单查询
     */
    @POST("api/v1/order/query")
    fun queryAllOrder(@Body requestBody: RequestBody): Observable<BaseModel<OrderMultipleModel>>
//
//    /**
//     * 订单详情查询
//     */
//    @GET("api/v1/order/details/query")
//    fun queryOrderDetail(@Query("orderNo") orderNum: String): Observable<BaseModel<OrderMultipleModel.ResultsBean>>
//
//    /**
//     * 订单驳回
//     */
//    @POST("api/v1/order/refuse")
//    fun refuseOrder(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 订单确认
//     */
//    @POST("api/v1/order/comfirm")
//    fun confirmOrder(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 订单完成
//     */
//    @POST("api/v1/order/complete")
//    fun successOrder(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 去发货
//     */
//    @POST("api/v1/order/ship")
//    fun sendDrug(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 微信扫码支付
//     */
//    @POST("api/v1/bizTrans/wxPayQr4Pad")
//    fun wechatPay(@Body requestBody: RequestBody): Observable<BaseModel<WeChatPayModel>>
//
//    /**
//     * pos机扫码支付
//     */
//    @GET("api/v1/bizTrans/qrImage")
//    fun posPay(@Query("msg") orderNum: String): Observable<BaseModel<PosPayModel>>
//
//    /**
//     * 查询支付结果
//     */
//    @GET("api/v1/bizTrans/wxPayQrQuery")
//    fun payResult(@Query("orderNo") orderNo: String): Observable<BooleanModel>
//
//    /**
//     * 招募活动详情
//     */
//    @GET("api/v1/recruitPoint/query/detailes")
//    fun queryRecruitDetail(@Query("id") id: String): Observable<BaseModel<RecruitDetailModel>>
//
//    /**
//     * 增加活动点击量
//     */
//    @GET("api/v1/recruitPoint/add/clickTimes")
//    fun addClickNum(@Query("id") id: String): Observable<BaseModel<Void>>
//
//    /**
//     * 上传问券招募的照片
//     */
//    @POST("api/v1/questionnaire/offline/upload/base64/file")
//    fun uploadRecruitImg(@Header("ossType") ossType: String, @Body requestBody: RequestBody): Observable<BaseModel<UploadIdCardModel>>
//
//    /**
//     * 增加患者
//     */
//    @POST("api/v1/patient/info/save")
//    fun addUser(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 查询消息列表
//     */
//    @POST("api/v1/message/list")
//    fun queryMsgList(@Body requestBody: RequestBody): Observable<BaseModel<MsgModel>>
//
//    /**
//     * 查询消息发送者信息
//     */
//    @GET("api/v1/message/sender/info")
//    fun queryMsgInfo(@Query("sender") sender: String): Observable<BaseModel<MsgInfoModel>>
//
//    /**
//     * 更新消息已读状态
//     */
//    @POST("api/v1/message/update")
//    fun updateMsg(@Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 合同协议签名
//     */
//    @POST("api/v1/service/agreement/sign")
//    fun uploadSign(@Header("ossType") ossType: String, @Body requestBody: RequestBody): Observable<BaseModel<Void>>
//
//    /**
//     * 获取合同协议
//     */
//    @GET("api/v1/service/agreement/info")
//    fun getServiceInfo(@Header("ossType") ossType: String): Observable<BaseModel<UploadIdCardModel>>
//
//    /**
//     * 下非会员单
//     */
//    @POST("api/v1/orderRecordTemp/insert")
//    fun payUnMemberOrder(@Body requestBody: RequestBody): Observable<BaseModel<UmMemberOrderModel>>
//
//    /**
//     * 上传处方照片
//     */
//    @POST("api/v1/upload/partnerDatumImage/savePrescription")
//    fun uploadPartnerImg(@Body requestBody: RequestBody): Observable<BaseModel<UmMemberOrderModel>>
//
//    /**
//     * 处方信息
//     */
//    @GET("api/v1/prescription/queryPrescriptionImage")
//    fun queryPrescriptionModel(@Query("prescriptionId") presId: String): Observable<BaseModel<PrescriptionModel>>
//
//    /**
//     * 版本更新
//     */
//    @POST
//    fun updateVersion(@Url url: String, @Body requestBody: RequestBody): Observable<BaseModel<AppModel>>
//
//    /**
//     * 上传发票
//     */
//    @POST("api/v1/order/insurance/wesure/uploadBase64File")
//    fun uploadInvoice(@Body requestBody: RequestBody): Observable<BaseModel<UploadIdCardModel>>
}