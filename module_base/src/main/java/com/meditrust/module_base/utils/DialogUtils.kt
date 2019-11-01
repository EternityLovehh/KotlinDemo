package com.meditrust.module_base.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.meditrust.module_base.MyApplication
import com.meditrust.module_base.R
import com.meditrust.module_base.Router
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.dialog.BindViewHolder
import com.meditrust.module_base.dialog.CommonDialog
import com.meditrust.module_base.listener.OnDialogBindViewListener
import com.meditrust.module_base.listener.OnDialogViewClickListener
import com.meditrust.module_base.manager.ActivityManager

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/28
 * @desc:
 */
object DialogUtils {

    fun showBindDialog(context: Context, content: String) {
        val fragmentManager: FragmentManager =
            (ActivityManager.instance?.currentActivity()!! as FragmentActivity).supportFragmentManager
        CommonDialog.Builder(fragmentManager)
            .setLayoutRes(R.layout.dialog_custom)
            .setTag(Const.UNBIND_PHARMACY)
            .setScreenWidthAspect(MyApplication.instance, 0.8f)
            .setScreenHeightAspect(MyApplication.instance, 0.2f)
            .addOnClickListener(intArrayOf(R.id.tv_cancel, R.id.tv_confirm))
            .setOnBindViewListener(object : OnDialogBindViewListener {
                override fun bindView(viewHolder: BindViewHolder) {
                    viewHolder.setText(R.id.tv_content, content)
                    viewHolder.setText(R.id.tv_confirm, "绑定")
                }

            })
            .setOnViewClickListener(object : OnDialogViewClickListener {
                override fun onDialogViewClick(
                    viewHolder: BindViewHolder,
                    view: View,
                    commonDialog: CommonDialog
                ) {
                    when (view.id) {
                        R.id.tv_cancel -> commonDialog.dismiss()
                        R.id.tv_confirm -> {
                            commonDialog.dismiss()
                            Router.startBindActivity()
                        }
                    }
                }

            })
            .create()
            .show()
    }
}