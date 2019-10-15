package com.meditrust.module_base.utils

import android.content.Context
import android.content.pm.PackageManager
import com.meditrust.module_base.MyApplication

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/2
 * @desc: 包管理工具类
 */
object PackageUtils {

    /**
     *  获取应用程序版本名称信息
     * @return 当前应用的版本名称
     */
    val packageName: String?
        get() {
            try {
                val packageManager = MyApplication.instance.getPackageManager()
                val packageInfo = packageManager.getPackageInfo(
                    MyApplication.instance.getPackageName(), 0
                )
                return packageInfo.packageName
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

    /**
     * 获得软件版本号
     */
    val versionCode: Int
        get() {
            var versioncode = 0
            try {
                versioncode = MyApplication.instance.getPackageManager().getPackageInfo(
                    MyApplication.instance.getPackageName(), 0
                ).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return versioncode
        }

    /**
     * 获取版本名称
     *
     * @param context
     * @return
     */
    fun getVersionName(context: Context): String? {
        var versionName: String? = null
        try {
            versionName = context.packageManager.getPackageInfo(
                context.packageName, 0
            ).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return versionName
    }


}
