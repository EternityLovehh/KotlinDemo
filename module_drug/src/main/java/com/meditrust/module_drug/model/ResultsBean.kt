package com.meditrust.module_drug.model

import com.google.gson.annotations.SerializedName
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_drug.adapter.OrderAdapter
import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/8/14
 * @desc: 订单父类
 */

data class ResultsBean(
    /**
     * actualAmount : 56040
     * carrier :
     * carrierPhone :
     * cityCode : 140100
     * confimTime : null
     * consumePoint : 0
     * couponAmount : 0
     * createTime : 2019-08-14 10:36:45
     * createUser : e00000M20320190114144935
     * discount : 1
     * discountAmount : 0
     * districtCode : 140106
     * expectFreightDate : 2019-08-14 00:00:00
     * expressCompany :
     * freightType : 01
     * fullAddress : 山西省太原市迎泽区红红
     * homedeliveryOrderDetailResp : [{"actualAmount":840,"commodityDesc":"","commodityDetailImageUrl":"","commodityId":5539,"commodityImageUrl":"","commodityName":"度易达®(度拉糖肽注射液)","commodityPrice":840,"commodityRemark":"","commodityStockQty":999999,"discount":1,"discountAmount":0,"discountPrice":840,"drugCategory":"处方","drugCommonName":"度拉糖肽注射液","drugEnglishName":"trulicity","drugId":5613,"drugImageUrl":"","drugLicenseNumber":"","drugManufacturer":"礼来","drugName":"度易达®(度拉糖肽注射液)","drugSku":"trulicity134","drugSmallImageUrl":"","drugSpec":"1.5mg","drugUnit":"mg","id":3159,"imageUrls":[],"orderNo":"300000K16520190814103645","price":840,"quantity":1,"remark1":"","remark10":"","remark11":"","remark12":"","remark13":"","remark14":"","remark15":"","remark2":"","remark3":"","remark4":"","remark5":"","remark6":"","remark7":"","remark8":"","remark9":"","searchKeywords":"度易达®(度拉糖肽注射液) trulicity 度拉糖肽注射液 2型糖尿病 trulicity134","totalAmount":840,"userId":"e00000M20320190114144935"},{"actualAmount":55200,"commodityDesc":"","commodityDetailImageUrl":"","commodityId":5537,"commodityImageUrl":"","commodityName":"可善挺®（司库奇尤单抗）","commodityPrice":9200,"commodityRemark":"","commodityStockQty":999999,"discount":1,"discountAmount":0,"discountPrice":9200,"drugCategory":"皮肤","drugCommonName":"苏金单抗","drugEnglishName":"","drugId":5611,"drugImageUrl":"","drugLicenseNumber":"","drugManufacturer":"诺华","drugName":"可善挺®（司库奇尤单抗）","drugSku":"cosentyx123","drugSmallImageUrl":"","drugSpec":"150mg/ml","drugUnit":"mg","id":3160,"imageUrls":[],"orderNo":"300000K16520190814103645","price":9200,"quantity":6,"remark1":"","remark10":"","remark11":"","remark12":"","remark13":"","remark14":"","remark15":"","remark2":"","remark3":"","remark4":"","remark5":"","remark6":"","remark7":"","remark8":"","remark9":"","searchKeywords":"可善挺®（司库奇尤单抗）  苏金单抗  cosentyx123","totalAmount":55200,"userId":"e00000M20320190114144935"}]
     * id : 2251
     * invoiceInfo :
     * invoiceType :
     * isDelete : 0
     * obtainPoint : 0
     * orderNo : 300000K16520190814103645
     * orderSource : 00
     * orderTime : 2019-08-14 10:36:45
     * orderType : 10
     * patientIdNum : Z6je6mUysUOn2h4hGIzmuvVvzoWFEevs
     * patientMobile : 17621798105
     * patientName : 黄其忠
     * patientSex : m
     * payImage :
     * payResult :
     * payStatus : false
     * payTime : null
     * payType :
     * pharmacyChannelType : SY
     * pharmacyId : 5
     * predictFreightDate : null
     * prescriptionId : 3280
     * provinceCode : 140000
     * receiver : 给您
     * receiverMobile : 17621798105
     * receiverTel :
     * remark :
     * remark1 :
     * remark2 :
     * remark3 :
     * remark4 :
     * remark5 : N
     * status : 02
     * taxFee : 0
     * totalAmount : 56040
     * updateTime : 2019-08-14 10:36:45
     * updateUser : 13476247611
     * userId : e00000M20320190114144935
     * waybillNo :
     */
    var actualAmount: Double = 0.toDouble(),
    var carrier: String? = null,
    var carrierPhone: String? = null,
    var cityCode: String? = null,
    var confimTime: Any? = null,
    var consumePoint: Double = 0.toDouble(),
    var couponAmount: Double = 0.toDouble(),
    var createTime: String? = null,
    var createUser: String? = null,
    var discount: Double = 0.toDouble(),
    var discountAmount: Double = 0.toDouble(),
    var districtCode: String? = null,
    var expectFreightDate: String? = null,
    var expressCompany: String? = null,
    var freightType: String? = null,
    var fullAddress: String? = null,
    var id: Int = 0,
    var invoiceInfo: String? = null,
    var invoiceType: String? = null,
    var isDelete: String? = null,
    var obtainPoint: Double = 0.toDouble(),
    var orderNo: String? = null,
    var orderSource: String? = null,
    var orderTime: String? = null,
    var orderType: String? = null,
    var patientIdNum: String? = null,
    var patientMobile: String? = null,
    var patientName: String? = null,
    var patientSex: String? = null,
    var payImage: String? = null,
    var payResult: String? = null,
    var isPayStatus: Boolean = false,
    var payTime: Any? = null,
    var payType: String? = null,
    var pharmacyChannelType: String? = null,
    var pharmacyId: String? = null,
    var predictFreightDate: Any? = null,
    var prescriptionId: Int = 0,
    var provinceCode: String? = null,
    var receiver: String? = null,
    var receiverMobile: String? = null,
    var receiverTel: String? = null,
    var remark: String? = null,
    var remark1: String? = null,
    var remark2: String? = null,
    var remark3: String? = null,
    var remark4: String? = null,
    var remark5: String? = null,
    var status: String? = null,
    var taxFee: Double = 0.toDouble(),
    var totalAmount: Double = 0.toDouble(),
    var updateTime: String? = null,
    var updateUser: String? = null,
    var userId: String? = null,
    var waybillNo: String? = null,
    var homedeliveryOrderDetailResp: ArrayList<HomedeliveryOrderDetailRespBean>,
    var pbmUser: PbmUserBean? = null,
    val mType: Int = OrderAdapter.TYPE_HEADER

) : BaseItem(), Serializable


data class PbmUserBean(
    /**
     * id : e00000M20320190114144935
     * name : cvgb
     * phoneNo : 17621798105
     */

    @SerializedName("id")
    var idX: String? = null,
    var name: String? = null,
    var phoneNo: String? = null
) : Serializable


data class HomedeliveryOrderDetailRespBean(
    /**
     * actualAmount : 840
     * commodityDesc :
     * commodityDetailImageUrl :
     * commodityId : 5539
     * commodityImageUrl :
     * commodityName : 度易达®(度拉糖肽注射液)
     * commodityPrice : 840
     * commodityRemark :
     * commodityStockQty : 999999
     * discount : 1
     * discountAmount : 0
     * discountPrice : 840
     * drugCategory : 处方
     * drugCommonName : 度拉糖肽注射液
     * drugEnglishName : trulicity
     * drugId : 5613
     * drugImageUrl :
     * drugLicenseNumber :
     * drugManufacturer : 礼来
     * drugName : 度易达®(度拉糖肽注射液)
     * drugSku : trulicity134
     * drugSmallImageUrl :
     * drugSpec : 1.5mg
     * drugUnit : mg
     * id : 3159
     * imageUrls : []
     * orderNo : 300000K16520190814103645
     * price : 840
     * quantity : 1
     * remark1 :
     * remark10 :
     * remark11 :
     * remark12 :
     * remark13 :
     * remark14 :
     * remark15 :
     * remark2 :
     * remark3 :
     * remark4 :
     * remark5 :
     * remark6 :
     * remark7 :
     * remark8 :
     * remark9 :
     * searchKeywords : 度易达®(度拉糖肽注射液) trulicity 度拉糖肽注射液 2型糖尿病 trulicity134
     * totalAmount : 840
     * userId : e00000M20320190114144935
     */

    var actualAmount: Double = 0.toDouble(),
    var commodityDesc: String? = null,
    var commodityDetailImageUrl: String? = null,
    var commodityId: Int = 0,
    var commodityImageUrl: String? = null,
    var commodityName: String? = null,
    var commodityPrice: Double = 0.toDouble(),
    var commodityRemark: String? = null,
    var commodityStockQty: Int = 0,
    var discount: Double = 0.toDouble(),
    var discountAmount: Double = 0.toDouble(),
    var discountPrice: Double = 0.toDouble(),
    var drugCategory: String? = null,
    var drugCommonName: String? = null,
    var drugEnglishName: String? = null,
    var drugId: Int = 0,
    var drugImageUrl: String? = null,
    var drugLicenseNumber: String? = null,
    var drugManufacturer: String? = null,
    var drugName: String? = null,
    var drugSku: String? = null,
    var drugSmallImageUrl: String? = null,
    var drugSpec: String? = null,
    var drugUnit: String? = null,
    var id: Int = 0,
    var orderNo: String? = null,
    var price: Double = 0.toDouble(),
    var quantity: Int = 0,
    var remark1: String? = null,
    var remark10: String? = null,
    var remark11: String? = null,
    var remark12: String? = null,
    var remark13: String? = null,
    var remark14: String? = null,
    var remark15: String? = null,
    var remark2: String? = null,
    var remark3: String? = null,
    var remark4: String? = null,
    var remark5: String? = null,
    var remark6: String? = null,
    var remark7: String? = null,
    var remark8: String? = null,
    var remark9: String? = null,
    var searchKeywords: String? = null,
    var totalAmount: Double = 0.toDouble(),
    var userId: String? = null,
    var imageUrls: List<*>? = null,
    val mType: Int = OrderAdapter.TYPE_CONTENT
) : BaseItem(), Serializable


data class OrderFootModel(
    val mType: Int = OrderAdapter.TYPE_FOOTER,
    var actualAmount: Double = 0.toDouble(),
    var status: String? = null,
    var homedeliveryOrderDetailResp: List<HomedeliveryOrderDetailRespBean>? = null,
    var resultsBean: ResultsBean? = null
) : BaseItem(), Serializable
