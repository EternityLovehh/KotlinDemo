package com.meditrust.module_base.constant

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/6
 * @desc: 网络相关code
 */
object HttpCode {
    /**
     * 网络请求成功
     */
    val SUCCESS_CODE = "200000"
    /**
     * token失效
     */
    val FAIL_TOKEN = "400001"
    /**
     * 短信验证码失效
     */
    val FAIL_CODE = "4B2012"

    /**
     * 网络连接失败
     */
    val NETWORK_ERROR = 100000
    /**
     * 解析数据失败
     */
    val PARSE_ERROR = 1008
    /**
     * 网络问题
     */
    val BAD_NETWORK = 1007
    /**
     * 连接错误
     */
    val CONNECT_ERROR = 1006
    /**
     * 连接超时
     */
    val CONNECT_TIMEOUT = 1005

    /**
     * 连接超时时长
     */
    val OKHTTP_DEFAULT_TIMEOUT = 20
}
