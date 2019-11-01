package com.meditrust.module_drug.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/28
 * @desc:
 */
data class IntegtalModel(
    /**
     * current : 0
     * freeze : 0
     * proposed : 0
     * rank : 0
     * sum : 0
     */
    @Bindable
    var current: Int = 0,
    var freeze: Int = 0,
    var proposed: Int = 0,
    @Bindable
    var rank: Int = 0,
    var sum: Int = 0
) : Serializable, BaseObservable()