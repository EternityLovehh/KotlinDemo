package com.meditrust.module_base.utils

import android.content.Context
import android.content.Intent
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.ui.WebActivity

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/20
 * @desc:
 */
object WebUtils {

    fun startWeb(context: Context, url: String, title: String) {
        val mToken = SpUtils.getString(SpUtils.SP_KEYS.APP_TOKEN)
        val stringBuffer = StringBuffer(url).append("?token=").append(mToken)
        val intent = Intent(context, WebActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra(Const.WEB_URL, stringBuffer.toString())
        intent.putExtra(Const.WEB_TITLE, title)
        context.startActivity(intent)
    }

}