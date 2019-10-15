package com.meditrust.moudle_user.viewmodel

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import com.meditrust.module_base.Router
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.constant.Urls
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.manager.ActivityManager
import com.meditrust.module_base.utils.ProvateUtil
import com.meditrust.module_base.utils.SpUtils
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_base.utils.WebUtils
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.api.ApiService
import com.meditrust.moudle_user.ui.ForgetPwdActivity
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
 * @date: 2019/9/18
 * @desc:
 */
class LoginViewModel : BaseViewModel {
    var mDisposable: Disposable? = null
    val mCodeText: MutableLiveData<String> = MutableLiveData()
    val mCodeClickAble: MutableLiveData<Boolean> = MutableLiveData()
    val mCodeTextColor: MutableLiveData<Boolean> = MutableLiveData()
    var isResgisted: MutableLiveData<Boolean> = MutableLiveData()
    val mPwdVisible: MutableLiveData<Boolean> = MutableLiveData()
    var context: Context

    constructor(application: Application) : super(application) {
        mCodeTextColor.value = false
        mCodeClickAble.value = true
        isResgisted.value = false
        mCodeText.value = "发送验证码"
        mPwdVisible.value = false
        context = application.applicationContext
    }

    fun startLegalProtol(view: View) {
        WebUtils.startWeb(
            getApplication(),
            Urls.LEGAL_AGREEMENT,
            getApplication<Application>().getString(R.string.legal_agreement)
        )
    }

    fun startRegisterProtol(view: View) {
        WebUtils.startWeb(
            getApplication(),
            Urls.PLATFORM_REGISTERED,
            getApplication<Application>().getString(R.string.platform_registed)
        )
    }

    fun startServiceProtol(view: View) {
        WebUtils.startWeb(
            getApplication(),
            Urls.PLATFORM_SERVICE,
            getApplication<Application>().getString(R.string.platform_service)
        )
    }

    fun startPrivacyPolicy(view: View) {
        WebUtils.startWeb(
            getApplication(),
            Urls.PRIVACY_POLICY,
            getApplication<Application>().getString(R.string.privacy_policy)
        )
    }

    fun onPwdCheckChanged(buttonView: CompoundButton, isChecked: Boolean) {
        mPwdVisible.value = isChecked
    }

    fun forgetPwd(view: View) {
        context.startActivity(Intent(context, ForgetPwdActivity::class.java))
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

    fun disposable() {
        mDisposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    fun isRegistered(type: String, phone: String, smsCode: String?, password: String?) {
        disposable()
        //重新点击
        mCodeClickAble.postValue(true)
        //还原按钮
        mCodeTextColor.postValue(false)

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输入手机号码")
            return
        }
        if (!ProvateUtil.validateMobile(phone)) {
            ToastUtils.showToast("请输入正确的手机号")
            return
        }

        if (TextUtils.equals(type, Const.SEND_DRUG_HOME)) {
            if (TextUtils.isEmpty(smsCode)) {
                ToastUtils.showToast("请输入验证码")
                return
            }
        } else {
            if (TextUtils.isEmpty(password)) {
                ToastUtils.showToast("请输入密码")
                return
            }
        }

        ApiService.isResgistered(phone)
            .io_main()
            .subscribe({
                isResgisted.postValue(it.result as Boolean)
                if (TextUtils.equals(type, Const.SEND_DRUG_HOME)) {
                    login(phone, smsCode, null)
                } else {
                    login(phone, null, password)
                }
            }, {
                ToastUtils.showToast(it.message!!)
            })
            .add()
    }

    private fun login(phone: String, smsCode: String?, password: String?) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("phoneNo", phone)
            if (TextUtils.isEmpty(smsCode)) {
                jsonObject.put("appLoginType", "password")
                jsonObject.put("password", password)
            } else {
                jsonObject.put("appLoginType", "sms")
                jsonObject.put("smsCode", smsCode)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.login(Const.OSS_TYPE, requestBody)
            .io_main()
            .subscribeBy({
                SpUtils.putString(SpUtils.SP_KEYS.USER_PHONE, phone)
                password?.let {
                    SpUtils.putString(SpUtils.SP_KEYS.USER_PASSWORD, password)
                }
                it?.let { SpUtils.putString(SpUtils.SP_KEYS.APP_TOKEN, it.toString()) }

                ToastUtils.showToast(it.toString())
                if (isResgisted.value!!) {
                    Router.startMainActivity()
                    ActivityManager.instance?.currentActivity()?.finish()
                } else {
                    Router.startSetPwdActivity()
                }
            }, {
                ToastUtils.showToast(it)
            })
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

                Log.e("time", "已发送${60 - aLong!!}s" + "----" + mCodeText.value)

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

}