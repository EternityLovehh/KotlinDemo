package com.meditrust.module_drug.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/28
 * @desc:
 */
data class PharmacyDetailModel(
    /**
     * pharmacyAddress : string
     * pharmacyChannelType : string
     * pharmacyCharityEvents : string
     * pharmacyCityCode : string
     * pharmacyCode : string
     * pharmacyDistrictCode : string
     * pharmacyId : 0
     * pharmacyImageUrl : string
     * pharmacyLatitude : string
     * pharmacyLongitude : string
     * pharmacyMobile : string
     * pharmacyName : string
     * pharmacyOurService : string
     * pharmacyProvinceCode : string
     * pharmacyServiceTime : string
     * pharmacySmallImageUrl : string
     * pharmacyTelephone : string
     * remark1 : string
     * remark10 : string
     * remark11 : string
     * remark12 : string
     * remark13 : string
     * remark14 : string
     * remark15 : string
     * remark2 : string
     * remark3 : string
     * remark4 : string
     * remark5 : string
     * remark6 : string
     * remark7 : string
     * remark8 : string
     * remark9 : string
     */
    @Bindable
    var pharmacyAddress: String? = null,
    var pharmacyChannelType: String? = null,
    var pharmacyCharityEvents: String? = null,
    var pharmacyCityCode: String? = null,
    var pharmacyCode: String? = null,
    var pharmacyDistrictCode: String? = null,
    var pharmacyId: Int = 0,
    var pharmacyImageUrl: String? = null,
    var pharmacyLatitude: String? = null,
    var pharmacyLongitude: String? = null,
    var pharmacyMobile: String? = null,
    var pharmacyName: String? = null,
    var pharmacyOurService: String? = null,
    var pharmacyProvinceCode: String? = null,
    var pharmacyServiceTime: String? = null,
    var pharmacySmallImageUrl: String? = null,
    var pharmacyTelephone: String? = null,
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
    var remark9: String? = null
) : Serializable, BaseObservable()