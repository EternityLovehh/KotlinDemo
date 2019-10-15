package com.meditrust.module_base.utils

import android.content.Context
import com.meditrust.module_base.MyApplication

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/19
 * @desc: 尺寸转换工具类
 */
object DpUtils {
    /**
     * 根据手机分辨率从DP转成PX
     * @param dpValue
     * @return
     */
    fun dp2px(dpValue: Float): Int {
        val scale = MyApplication.instance.getResources().getDisplayMetrics().density
        return (dpValue * scale + 0.5f).toInt()
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    fun sp2px(spValue: Float): Int {
        val fontScale = MyApplication.instance.getResources().getDisplayMetrics().scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

    /**
     * 根据手机的分辨率PX(像素)转成DP
     *
     * @param pxValue
     * @return
     */
    fun px2dp(pxValue: Float): Int {
        val scale = MyApplication.instance.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     */

    fun px2sp(pxValue: Float): Int {
        val fontScale = MyApplication.instance.resources.displayMetrics.scaledDensity
        return (pxValue / fontScale + 0.5f).toInt()
    }

    /**
     * 获取屏幕宽度
     */
    fun getWidth(context: Context): Int {
        return context.resources.displayMetrics.widthPixels
    }

    /**
     * 获取屏幕宽度
     */
    fun getHeight(context: Context): Int {
        return context.resources.displayMetrics.heightPixels
    }

}