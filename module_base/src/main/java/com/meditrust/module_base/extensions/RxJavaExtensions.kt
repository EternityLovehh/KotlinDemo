package com.meditrust.module_base.extensions

import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.meditrust.module_base.MyApplication
import com.meditrust.module_base.R
import com.meditrust.module_base.Router
import com.meditrust.module_base.constant.HttpCode
import com.meditrust.module_base.dialog.BindViewHolder
import com.meditrust.module_base.dialog.CommonDialog
import com.meditrust.module_base.listener.OnDialogViewClickListener
import com.meditrust.module_base.manager.ActivityManager
import com.meditrust.module_base.model.BaseModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc:rxjava扩展函数
 */

fun <T> Observable<T>.io_main(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <D, T : BaseModel<D>> Observable<T>.subscribeBy(onResponse: (D?) -> Unit, onFailure: (String) -> Unit): Disposable =
    subscribe({
        Log.e("respose", it.result.toString())
        when (it.code) {
            HttpCode.SUCCESS_CODE -> onResponse(it.result)
            HttpCode.FAIL_TOKEN -> {
                showLoginDialog()
                onFailure(it.message)
            }
            HttpCode.FAIL_CODE -> {
                onFailure(it.message)
            }
            else -> {
                onFailure(it.message)
            }
        }
    }, {
        onFailure(it.message!!)
        Log.e("respose", it?.message)
    })

fun <D, T : BaseModel<D>> Observable<T>.subscribe(
    onResponse: (T?) -> Unit,
    onFailure: (String) -> Unit
): Disposable =
    subscribe({
        Log.e("respose", it.result.toString())
        when (it.code) {
            HttpCode.SUCCESS_CODE -> onResponse(it)
            HttpCode.FAIL_TOKEN -> {
                showLoginDialog()
                onFailure(it.message)
            }
            HttpCode.FAIL_CODE -> {
                onFailure(it.message)
            }
            else -> {
                onFailure(it.message)
            }
        }
    }, {
        onFailure(it.message!!)
        Log.e("respose", it?.message)
    })

fun showLoginDialog() {
    val fragmentManager: FragmentManager =
        (ActivityManager.instance?.currentActivity()!! as FragmentActivity).supportFragmentManager
    CommonDialog.Builder(fragmentManager)
        .setLayoutRes(R.layout.dialog_custom)
        .setTag(HttpCode.FAIL_TOKEN)
        .setScreenWidthAspect(MyApplication.instance, 0.8f)
        .setScreenHeightAspect(MyApplication.instance, 0.2f)
        .addOnClickListener(intArrayOf(R.id.tv_cancel, R.id.tv_confirm))
        .setOnViewClickListener(object : OnDialogViewClickListener {
            override fun onDialogViewClick(viewHolder: BindViewHolder, view: View, commonDialog: CommonDialog) {
                when (view.id) {
                    R.id.tv_cancel -> commonDialog.dismiss()
                    R.id.tv_confirm -> {
                        commonDialog.dismiss()
                        Router.startLoginActivity()
                    }
                }
            }

        })
        .create()
        .show()
}




