package com.meditrust.moudle_user.viewmodel

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.manager.ActivityManager
import com.meditrust.module_base.utils.ProvateUtil
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.moudle_user.api.ApiService
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.util.concurrent.TimeUnit

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/9
 * @desc:
 */
class ForgetPwdViewModel : BaseViewModel {
    var mDisposable: Disposable? = null
    val mCodeText: MutableLiveData<String> = MutableLiveData()
    val mCodeClickAble: MutableLiveData<Boolean> = MutableLiveData()
    val mCodeTextColor: MutableLiveData<Boolean> = MutableLiveData()

    constructor(application: Application) : super(application) {
        mCodeTextColor.value = false
        mCodeClickAble.value = true
        mCodeText.value = "发送验证码"
    }

    fun getCodeTime(phone: String) {
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入手机号")
            return
        }
        if (!ProvateUtil.validateMobile(phone)) {
            ToastUtils.showToast("请输入正确的手机号")
            return
        }

        mCodeClickAble.postValue(false)

        val jsonObject = JSONObject()
        try {
            jsonObject.put("phoneNo", phone)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.getSmsCode(requestBody)
            .io_main()
            .subscribe({
                startTimer()
            }, {
                ToastUtils.showToast(it.message!!)
            })
            .add()
    }

    fun retrievePwd(phone: String, code: String, pwd: String, confirmPwd: String) {
        disposable()
        //重新点击
        mCodeClickAble.postValue(true)
        //还原按钮
        mCodeTextColor.postValue(false)

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请先输入手机号")
            return
        }
        if (!ProvateUtil.validateMobile(phone)) {
            ToastUtils.showToast("请输入正确的手机号")
            return
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.showToast("请输入新密码")
            return
        }
        if (TextUtils.isEmpty(confirmPwd)) {
            ToastUtils.showToast("请再次输入新密码")
            return
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast("请输入验证码")
            return
        }

        val jsonObject = JSONObject()
        try {
            jsonObject.put("phoneNo", phone)
            jsonObject.put("smsAuthCode", code)
            jsonObject.put("newPassword", pwd)
            jsonObject.put("confirmNewPassword", confirmPwd)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.retrievePwd(requestBody)
            .io_main()
            .subscribeBy(
                {
                    ToastUtils.showToast("密码重置成功，请重新登录")
                    ActivityManager.instance?.finishActivity()
                },
                {
                    ToastUtils.showToast(it)
                }
            )
            .add()
    }


    private fun startTimer() {
        mDisposable = Flowable.intervalRange(0, 60, 0, 1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { aLong ->
                //设置按钮为灰
                mCodeTextColor.postValue(true)
                //设置倒计时时间
                mCodeText.postValue("已发送${(60 - aLong!!)}s")
            }
            .doOnComplete {
                mCodeText.postValue("发送验证码")
                //重新点击
                mCodeClickAble.postValue(true)
                //还原按钮
                mCodeTextColor.postValue(false)
                disposable()
            }
            .subscribe()
    }

    fun disposable() {
        mDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }
}