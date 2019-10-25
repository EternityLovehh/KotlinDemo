package com.meditrust.module_base.extensions

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

fun <VH : RecyclerView.ViewHolder, A : RecyclerView.Adapter<VH>> RecyclerView.init(
    adapter: A,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
        context
    )
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}