package com.meditrust.module_drug.bean

import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/8/16
 * @desc: 确认订单请求实体
 */
class ConfirmOrderBean : Serializable {

    /**
     * cutAmount : string
     * homedeliveryOrderConfirmDetailReqList : [{"id":"string","price":"string","quantity":"string"}]
     * orderNo : string
     * remark : string
     */

    var cutAmount: String? = null
    var orderNo: String? = null
    var remark: String? = null
    var homedeliveryOrderConfirmDetailReqList: List<HomedeliveryOrderConfirmDetailReqListBean>? =
        null

    class HomedeliveryOrderConfirmDetailReqListBean {
        /**
         * id : string
         * price : string
         * quantity : string
         */

        var id: String? = null
        var price: String? = null
        var quantity: String? = null
    }
}
