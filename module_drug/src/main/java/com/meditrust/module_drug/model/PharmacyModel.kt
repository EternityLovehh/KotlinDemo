package com.meditrust.module_drug.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/8/1
 * @desc: 药师信息实体
 */
data class PharmacyModel(
    /**
     * avator : string
     * birthday : 2019-08-01T02:13:55.310Z
     * idNum : string
     * isManager : string
     * nickName : string
     * pharmacyId : 0
     * phoneNo : string
     * qrValue : string
     * self : true
     * sex : string
     * userId : string
     * userName : string
     * userStatus : string
     */
    var avator: String,
    var birthday: String,
    var idNum: String,
    var isManager: String,
    @Bindable
    var nickName: String,
    var pharmacyId: Long = 0,
    var phoneNo: String,
    var qrValue: String,
    var isSelf: Boolean = false,
    var sex: String,
    var userId: String,
    @Bindable
    var userName: String,
    var userStatus: String,
    var remark1: String
) : Serializable, BaseObservable()
