package com.meditrust.module_base

import android.content.Intent
import com.alibaba.android.arouter.launcher.ARouter
import com.meditrust.module_base.manager.ActivityManager
import java.lang.reflect.InvocationTargetException

object Router {
    fun startMainActivity() {
        ARouter.getInstance().build("/app/MainActivity").navigation()
    }

    fun startSetPwdActivity() {
        ARouter.getInstance().build("/user/SetPwdActivity").navigation()
    }

    fun startNickNmaeActivity() {
        ARouter.getInstance().build("/user/NickNameActivity").navigation()
    }

    fun startLoginActivity() {
        try {
            val clz = Class.forName("com.meditrust.moudle_user.ui.LoginActivity")
            ActivityManager.instance?.currentActivity()
                ?.startActivity(Intent(ActivityManager.instance?.currentActivity(), clz))
            ActivityManager.instance?.finishOtherActivity(clz)
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }

    fun startBindActivity() {
        ARouter.getInstance().build("/user/NickNameActivity").navigation()
    }
}