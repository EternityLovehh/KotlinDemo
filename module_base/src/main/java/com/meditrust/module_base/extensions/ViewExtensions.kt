package com.meditrust.module_base.extensions

import android.view.View

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc: 扩展view
 */

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.visibility(visible: Boolean) {
    if (visible) {
        visible()
    } else {
        gone()
    }
}