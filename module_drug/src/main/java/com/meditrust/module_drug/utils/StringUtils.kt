package com.meditrust.module_drug.utils

import com.meditrust.module_base.constant.Const
import com.meditrust.module_drug.common.OrderStatus

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/24
 * @desc:
 */
object StringUtils {

    @JvmStatic
    fun orderType(type: String?): String? {
        return when (type) {
            OrderStatus.UN_AUDIT_ORDER -> "待审核"
            OrderStatus.HAVE_DELIVERY_ORDER -> "已发货"
            OrderStatus.UN_DELIVERY_ORDER -> "待发货"
            OrderStatus.UN_PAY, OrderStatus.PAY_FAIL -> "未付款"
            OrderStatus.UN_DELIVERY_AND_PAY -> "待发货（未付款）"
            OrderStatus.UN_TAKE_DRUG -> "待取药"
            OrderStatus.ORDER_SUCCESS -> "已完成"
            OrderStatus.HAVE_DELIVERY_AND_PAY -> "已发货（未付款）"
            OrderStatus.HAVE_REFUSE -> "已驳回"
            OrderStatus.REFUND_ON -> "退款中"
            OrderStatus.HAVE_REFUND -> "已退款"
            OrderStatus.REFUND_FAIL -> "退款失败"
            else -> ""
        }
    }

    @JvmStatic
    fun invoiceType(type: String?): String? {
        return when (type) {
            Const.UN_BIND_PM -> "发票审核完成"
            Const.REFUSED_PM -> "待审核发票"
            else -> ""
        }
    }
}
