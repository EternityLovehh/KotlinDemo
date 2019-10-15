package com.meditrust.moudle_user.viewmodel

import android.app.Application
import android.text.TextUtils
import com.meditrust.module_base.Router
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.manager.ActivityManager
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
class NickNameViewModel : BaseViewModel {

    constructor(application: Application) : super(application)

    fun setNickName(nickName: String) {
        if (TextUtils.isEmpty(nickName)) {
            ToastUtils.showToast("名称不能为空")
            return
        }
        if (!ProvateUtil.validateNickname(nickName)) {
            ToastUtils.showToast("请输入正确的名称")
            return
        }

        val jsonObject = JSONObject()
        try {
            jsonObject.put("nickName", nickName)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())

        ApiService.savePwd(requestBody)
            .io_main()
            .subscribe(
                {
                    Router.startMainActivity()
                    val clz = Class.forName("com.meditrust.kotlindemo.MainActivity")
                    ActivityManager.instance?.finishOtherActivity(clz)
                },
                {
                    ToastUtils.showToast(it.message!!)
                }
            )
            .add()

    }
}
