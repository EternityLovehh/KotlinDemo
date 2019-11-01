package com.meditrust.module_drug.model

import com.meditrust.module_base.model.BaseItem

/**
 * @author: create by zhongchao.wang
 * @date: 2019/11/1
 * @desc:
 */
data class MsgModel(
    /**
     * content : string
     * createTime : 2019-08-22T01:10:42.790Z
     * id : 0
     * isReaded : string
     * remark1 : string
     * remark2 : string
     * remark3 : string
     * remark4 : string
     * remark5 : string
     * sender : string
     * type : string
     * userId : string
     */
    var content: String? = null,
    var createTime: String? = null,
    var id: Int = 0,
    var isReaded: String? = null,
    var remark1: String? = null,
    var remark2: String? = null,
    var remark3: String? = null,
    var remark4: String? = null,
    var remark5: String? = null,
    var sender: String? = null,
    var type: String? = null,
    var userId: String? = null
) : BaseItem()