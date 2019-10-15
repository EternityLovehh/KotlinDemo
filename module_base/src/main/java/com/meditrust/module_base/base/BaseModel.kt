package com.meditrust.module_base.base

import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/20
 * @desc: 基类model
 */
data class BaseModel<T>(
    var code: String,
    var message: String,
    var subMessage: String,
    var success: Boolean,
    var result: T
) : Serializable