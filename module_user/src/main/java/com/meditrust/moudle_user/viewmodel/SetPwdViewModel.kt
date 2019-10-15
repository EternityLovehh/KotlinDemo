package com.meditrust.moudle_user.viewmodel

import android.app.Application
import android.text.TextUtils
import com.meditrust.module_base.Router
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.utils.ProvateUtil
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.moudle_user.api.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/9
 * @desc:
 */
class SetPwdViewModel : BaseViewModel {

    constructor(application: Application) : super(application)

    fun setPwd(pwd: String, confirmPwd: String) {
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showToast("请输入密码")
            return
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            ToastUtils.showToast("请再次输入密码")
            return
        }

        val isFirstRight = ProvateUtil.validatePwd(getApplication(), pwd)
        val isSecondRight = ProvateUtil.validatePwd(getApplication(), confirmPwd)

        if (!isFirstRight || !isSecondRight) {
            ToastUtils.showToast("请输入6-16位的字母数字的密码")
            return
        }
        if (pwd != confirmPwd) {
            ToastUtils.showToast("两次输入的密码不一致")
            return
        }

        val jsonObject = JSONObject()
        try {
            jsonObject.put("password", pwd)
            jsonObject.put("confirmPassword", confirmPwd)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())

        ApiService.savePwd(requestBody)
            .io_main()
            .subscribe(
                {
                    Router.startNickNmaeActivity()
                    ToastUtils.showToast(it.message)
                },
                {
                    ToastUtils.showToast(it.message!!)
                }
            )
            .add()

    }
}
