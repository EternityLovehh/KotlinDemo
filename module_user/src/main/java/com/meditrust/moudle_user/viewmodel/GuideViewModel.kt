package com.meditrust.moudle_user.viewmodel

import android.app.Activity
import android.app.Application
import android.view.View
import com.meditrust.module_base.Router
import com.meditrust.module_base.base.BaseViewModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc:
 */
class GuideViewModel(application: Application) : BaseViewModel(application) {

    fun startMain(view: View) {
        Router.startMainActivity()
        val context = view.context as Activity
        context.finish()
    }
}