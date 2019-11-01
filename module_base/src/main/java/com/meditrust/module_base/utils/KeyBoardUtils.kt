package com.meditrust.module_base.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/31
 * @desc:
 */
object KeyBoardUtils {

    /**
     * 点击屏幕空白处隐藏键盘
     */
    fun hideKeyBoard(event: MotionEvent, view: View, activity: Activity) {
        try {
            if (view is EditText) {
                val location: IntArray = intArrayOf(0, 0)
                view.getLocationInWindow(location)
                val left = location[0]
                val top = location[1]
                val right = left + view.width
                val bottom = top + view.height
                //判断焦点位置坐标是否在空间内，如果位置在控件外，则隐藏键盘
                if (event.rawX < left || event.rawX > right || event.y < top || event.y > bottom) {
                    val inputMethodManager: InputMethodManager =
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(
                        view.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}