package com.meditrust.module_base.constant

import com.meditrust.module_base.BuildConfig

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc: web页面url
 */
object Urls {
    val BASE_OTHER_URL = BuildConfig.BASE_UPDATE + "gateway/api-data/api/v1/app/upgrade"
    //新增药房
    val ADD_PHARMACY = BuildConfig.BASE_WEB_URL + "#/pages/choosePharmacy/addnewPharmacy"

    //新增药品
    val ADD_DRUG = BuildConfig.BASE_WEB_URL + "#/pages/drugLibrary/addDrug"

    //积分排行
    val INTEGRAL_RANK = BuildConfig.BASE_WEB_URL + "#pages/personalCenter/ranking"

    //积分记录
    val INTEGRAL_HISTORY = BuildConfig.BASE_WEB_URL + "#pages/withdraw/pointRecord"

    //订单对账
    val ORDER_RECIPT = BuildConfig.BASE_WEB_URL + "#pages/personalCenter/reconciliation"

    //药品详情
    val DRUG_DETAIL = BuildConfig.BASE_WEB_URL + "#/pages/allOrders/commodityDetail"

    //提现规则
    val MONEY_RULES = BuildConfig.BASE_HTML_URL + "MTHYSD/static/moneyRules.html"

    //提现教程
    val INTEGRAL_TURORIAL = BuildConfig.BASE_HTML_URL + "MTHYSD/static/teachVideo.html"

    //积分规则
    val INTEGRAL_RULES = BuildConfig.BASE_HTML_URL + "MTHYSD/static/integralRules.html"

    //我的福利
    val MY_WELFARE = BuildConfig.BASE_HTML_URL + "MTHYSD/static/basicYsb.html"

    //关于我们
    val ABOUT_US = BuildConfig.BASE_HTML_URL + "MTHYSD/static/aboutUs.html"

    //注册法律风险
    val LEGAL_AGREEMENT = BuildConfig.BASE_HTML_URL + "MTHYSD/static/legalRisk.html"

    //平台注册协议
    val PLATFORM_REGISTERED = BuildConfig.BASE_HTML_URL + "MTHYSD/static/platformRegistered.html"

    //平台服务协议
    val PLATFORM_SERVICE = BuildConfig.BASE_HTML_URL + "MTHYSD/static/platformService.html"

    //隐私政策
    val PRIVACY_POLICY = BuildConfig.BASE_HTML_URL + "MTHYSD/static/privacy.html"
}