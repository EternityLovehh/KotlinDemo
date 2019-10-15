package com.meditrust.module_base.utils

import android.content.Context
import com.meditrust.module_base.MyApplication

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc: 数据存储工具类
 */
object SpUtils {

    private val sharedPreferences =
        MyApplication.instance.getSharedPreferences(SP_KEYS.CONFIG_FILE_NAME, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).commit()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).commit()
    }

    fun getInt(key: String): Int? {
        return sharedPreferences.getInt(key, 0)
    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).commit()
    }

    fun getBoolean(key: String): Boolean? {
        return sharedPreferences.getBoolean(key, false)
    }

    fun clear() {
        sharedPreferences.edit().clear().commit()
    }


    object SP_KEYS {
        val CONFIG_FILE_NAME = "config"
        val APP_TOKEN = "app_token"
        private val FILE_NAME = "setting_data"
        val IS_REGISTED = "is_registed"

        val USER_ID = "userid"
        val USER_NAME = "username"
        val USER_PHONE = "user_phone"
        val USER_ADDRESS = "user_address"
        val USER_PASSWORD = "user_password"
        val PHARMACY_NAME = "pharmacy_name"
        val PHARMACY_ID = "pharmacy_id"

        val MSG_CONTENT = "msg_content"
        val MSG_COUNT = "msg_count"
        val MSG_TIME = "msg_time"

        val WECHAT_NAME = "wechat_name"
        val WECHAT_PORTRAIT = "wechat_portrait"

        val PERSION_QR_CODE = "persion_code"
        val QR_VALUE = "qr_value"
        val VERSION_CODE = "version_code"
    }
}