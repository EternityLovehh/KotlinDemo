package com.meditrust.module_drug.model

import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/11/1
 * @desc:
 */
data class RecruitDetailModel(
    /**
     * content : string
     * qrUrl : string
     */

    var content: String? = null,
    var qrUrl: String? = null
) : Serializable