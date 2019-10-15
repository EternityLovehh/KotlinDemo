package com.meditrust.module_base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.meditrust.module_base.component.AppComponent
import com.meditrust.module_base.component.DaggerAppComponent
import com.meditrust.module_base.utils.PackageUtils
import com.meditrust.module_base.utils.SpUtils
import kotlin.properties.Delegates

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/4
 * @desc:
 */
open class MyApplication : Application() {

    companion object {
        var instance: MyApplication by Delegates.notNull()
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
        SpUtils.putInt(SpUtils.SP_KEYS.VERSION_CODE, PackageUtils.versionCode)
    }
}