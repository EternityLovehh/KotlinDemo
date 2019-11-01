package com.meditrust.module_drug.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/31
 * @desc:
 */
data class MsgInfoModel(
    /**
     * avator : string
     * content : string
     * createTime : 2019-08-23T07:21:58.515Z
     * quantity : 0
     * sender : string
     * userName : string
     */

    var avator: String? = null,
    @Bindable
    var content: String? = null,
    @Bindable
    var createTime: String? = null,
    @Bindable
    var quantity: Int = 0,
    var sender: String? = null,
    var userName: String? = null
) : Serializable, BaseObservable()
