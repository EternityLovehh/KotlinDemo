package com.meditrust.module_drug.common

/**
 * @author: create by zhongchao.wang
 * @date: 2019/8/14
 * @desc: 订单状态常量类
 */
object OrderStatus {
    //待审核
    val UN_AUDIT_ORDER = "02"
    //审核不通过
    val AUDIT_FAIL = "03"
    //待发货
    val UN_DELIVERY_ORDER = "04"
    //支付失败
    val PAY_FAIL = "05"
    //支付中
    val PAY_ON = "06"
    //已发货
    val HAVE_DELIVERY_ORDER = "07"
    //已完成
    val ORDER_SUCCESS = "08"
    //待付款
    val UN_PAY = "09"
    //已付款
    val HAVE_PAY = "10"
    //已驳回
    val HAVE_REFUSE = "11"
    //退款中
    val REFUND_ON = "20"
    //已退款
    val HAVE_REFUND = "21"
    //退款失败
    val REFUND_FAIL = "22"
    //订单取消
    val CANCEL_ORDER = "12"
    //待发货（未付款）
    val UN_DELIVERY_AND_PAY = "13"
    //已发货（未付款）
    val HAVE_DELIVERY_AND_PAY = "14"
    //待取药
    val UN_TAKE_DRUG = "30"
}
