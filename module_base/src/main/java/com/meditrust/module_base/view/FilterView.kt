package com.meditrust.module_base.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.meditrust.module_base.R
import com.meditrust.module_base.extensions.visible
import com.meditrust.module_base.listener.OnTabClickListener
import kotlinx.android.synthetic.main.layout_filter_view.view.*

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/17
 * @desc:
 */
class FilterView : LinearLayout {

    private var onTabClickListener: OnTabClickListener? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.layout_filter_view, this)
        ll_switch_spec.setOnClickListener { onTabClickListener?.onTabClick(0) }
        ll_switch_price.setOnClickListener { onTabClickListener!!.onTabClick(1) }
        ll_filter_history.setOnClickListener { onTabClickListener?.onTabClick(2) }
    }

    /**
     * 显示三个选项菜单
     */
    fun setHistoryVisible() {
        ll_filter_history.visible()
    }

    fun setTabClickListener(onTabClickListener: OnTabClickListener) {
        this.onTabClickListener = onTabClickListener
    }

}