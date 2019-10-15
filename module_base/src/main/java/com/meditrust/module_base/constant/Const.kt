package com.meditrust.module_base.constant

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc:
 */
object Const {
    //网络请求pagesize
    val PAGE_SIZE = 10
    //选择照片
    val RESULT_LOAD_IMAGE = 100
    //拍照
    val TAKE_PHOTO = 101
    //上架历史记录
    val UP_HISTORY = 102
    //下架历史记录
    val DOWN_HISTORY = 103

    //积分提现成功
    val WITH_DRAWAL_CODE = 104
    //绑定药房成功
    val BIND_PHARMACY_CODE = 105

    val ITEM_CONTENT = 1
    val ITEM_FOOTER = 2

    val BUGLY_APP_ID = "77db99da89"
    val BUGLY_APP_KEY = "91a52db1-d4e2-4396-a659-c99160af0ffd"

    val JSON = "application/json; charset=utf-8"
    val OSS_TYPE = "android"

    val WEB_URL = "web_url"
    val WEB_TITLE = "web_title"
    val PROTOCOL_ID = "protocol_id"
    //药房绑定状态  未绑定
    val UN_BIND_PM = "00"
    //待审核
    val UN_AUDIT_PM = "01"
    //已拒绝
    val REFUSED_PM = "02"
    //已绑定
    val HAVE_BIND_PM = "03"
    //已冻结
    val HAVE_FORZEN_PM = "04"
    //获取协议
    val PROTOCOL_CODE = "YSD_APP_USER_AGREEMENT"
    //获取药品种类
    val DRUG_TYPE_CODE = "DRUG_CATAGORY"
    //获取驳回原因
    val REFUSE_CODE = "YSD_REFUSE_CAUSE"
    //获取提现规则
    val WITH_DRAW_RULES_CODE = "YSD_WITHDRAW_RULES_DES"
    //药房品牌
    val PHARMACY_CHANNEL_TYPE = "PHARMACY_CHANNEL_TYPE"
    //药房绑定
    val UNBIND_PHARMACY = "unbind_pharmacy"
    //药店id
    val PHARMACY_ID = "pharmacy_id"
    //药店名
    val PHARMACY_NAME = "pharmacy_name"
    //药店地址
    val PHARMACY_ADDRESS = "pharmacy_address"
    //药店详情
    val PHARMACY_INFO = "pharmacy_info"
    //userid
    val USER_ID = "userid"
    //username
    val USER_NAME = "username"
    //phone
    val USER_PHONE = "user_phone"
    //useraddress
    val USER_ADDRESS = "user_address"
    //是否为店长
    val IS_MANAGER = "is_manager"
    val USER_INFO = "user_info"

    val MANAGER = "Y"
    val NOT_MANAGER = "N"

    val USER_INTEGRAL = "user_integral"
    //招募类型
    val RECRUIT_TYPE = "recruit_type"
    //药品招募
    val DRUG_RECRUIT = "01"
    //保险招募
    val INSURAN_RECRUIT = "02"
    //问券招募
    val STAMP_RECRYUT = "03"

    val RESULT_TYPE = "result_type"
    //二维码弹框
    val QR_CODE = "qrcode"
    //打电话弹框
    val CALL_PHONE = "call_phone"
    //修改弹框
    val ALTER_DRUG = "alter_drug"
    //版本更新
    val UPDATE_VERSION = "update"
    //提现
    val WITH_DRAWAL_UNUSUAL = "with_drawal_unusal"

    val UP_OR_DOWN_HISTORY = "up_or_down"
    //药品信息
    val DRUG_INFO = "drug_info"
    //药品种类
    val DRUG_TYPE = "drug_type"
    //订单号
    val ORDER_NUM = "order_num"
    //订单类型
    val ORDER_TYPE = "order_type"
    //订单状态
    val ORDER_STATUS = "order_status"
    //订单信息
    val ORDER_INFO = "order_info"
    //运单号
    val WAYBILL_NUM = "way_bill_num"
    //招募活动id
    val RECRUIT_ID = "recruit_id"
    val RECRUIT_TITLE = "recruit_title"
    //送药到家
    val SEND_DRUG_HOME = "10"
    //到店自取
    val COME_STORE = "30"
    //消息发送者
    val SENDER = "system"
    //提现状态
    val WITH_DRAWAL_STATUS = "with_drawal_status"
    //未签字未上传照片
    val NO_SING_AND_IDCARD = "00"
    //已上传照片
    val HAVE_IDCARD = "01"
    //已签字
    val HAVE_SING = "02"
    //可提现
    val WITH_DRAWAL = "03"
    //账户被冻结
    val ACCOUNT_FREEZE = "04"
    //账户被注销
    val ACCOUNT_LOGOUT = "05"
    //存在冻结积分
    val HAVE_FREEZE_INTEGRAL = "06"
    //存在负分
    val NEGATIVE_POINT = "07"
    //今日已提现
    val HAVE_WITH_DRAWAL = "08"
    //标记fragment
    val FRAGMENT_ID = "fragment_id"
    //版本更新url
    val UPDATE_URL = "update_url"
    //二维码类型
    val CODE_TYPE = "code_type"
    //条形码
    val BAR_CODE = "bar_code"
    //上传照片
    val UPLOAD_TYPE = "upload_type"
    //发票
    val INVOICE_INFO = "invoice_info"
    //身份信息
    val IDCARD_INFO = "idcard_info"

    val MAN = "m"
    val WOMEN = "f"
}