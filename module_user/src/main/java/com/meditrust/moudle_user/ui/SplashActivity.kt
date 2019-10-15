package com.meditrust.moudle_user.ui

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.meditrust.module_base.Router
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.utils.PackageUtils
import com.meditrust.module_base.utils.SpUtils
import com.meditrust.moudle_user.R
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity<ViewDataBinding, BaseViewModel>() {
    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initData() {
        Observable.timer(1, TimeUnit.SECONDS)
            .io_main()
            .subscribe { aLong ->
                //判断是否第一次打开
                val oldVersion = SpUtils.getInt(SpUtils.SP_KEYS.VERSION_CODE)
                val currentVersion = PackageUtils.versionCode
                if (currentVersion > oldVersion!!) {
                    startActivity(Intent(this@SplashActivity, GuideActivity::class.java))
                    finish()
                } else {
                    start()
                }
            }
    }

    private fun start() {
        Router.startMainActivity()
        finish()
    }

    override fun initView(): Int {
        return R.layout.activity_splash
    }

    override fun initViewModel(): BaseViewModel {
        return BaseViewModel(application)
    }

    override fun initVariableId(): Int {
        return 0
    }

}
