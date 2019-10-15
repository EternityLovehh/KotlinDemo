package com.meditrust.module_base.listener

import android.view.View
import com.meditrust.module_base.dialog.BindViewHolder
import com.meditrust.module_base.dialog.CommonDialog

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc:
 */
interface OnDialogViewClickListener {
    fun onDialogViewClick(viewHolder: BindViewHolder, view: View, commonDialog: CommonDialog)
}