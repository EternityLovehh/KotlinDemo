package com.meditrust.module_base.utils

import android.widget.Toast
import com.meditrust.module_base.MyApplication

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/20
 * @desc: 文字提示框
 */
object ToastUtils {

    var mToast: Toast? = null

    fun showToast(text: String) {
        text?.let {
            if (mToast != null) {
                mToast!!.setText(text)
            } else {
                mToast = Toast.makeText(MyApplication.instance, text, Toast.LENGTH_SHORT)
            }
            mToast?.show()
        }
    }

}