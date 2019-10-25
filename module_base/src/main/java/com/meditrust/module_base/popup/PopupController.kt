package com.meditrust.module_base.popup

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.PopupWindow

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/17
 * @desc:
 */
class PopupController(private var context: Context, private var popupWindow: PopupWindow) {
    //布局id
    var layoutResId: Int = 0
    //弹窗布局View
    var mPopupView: View? = null
    var mView: View? = null
    var mWindow: Window? = null

    fun setView(layoutId: Int) {
        mView = null
        this.layoutResId = layoutId
        installContent()
    }

    fun setView(view: View) {
        mView = view
        this.layoutResId = 0
        installContent()
    }

    private fun installContent() {
        when {
            layoutResId != 0 -> mPopupView = LayoutInflater.from(context).inflate(layoutResId, null)
            mView != null -> mPopupView = mView
        }
        popupWindow.contentView = mPopupView
    }

    /**
     * 设置宽高
     */
    private fun setWidthAndHeight(width: Int, height: Int) {
        if (width == 0 || height == 0) {
            //如果没设置宽高，默认是WRAP_CONTENT
            popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
            popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            popupWindow.width = width
            popupWindow.height = height
        }
    }

    /**
     * 设置背景灰色程度
     * @param level 0.0f-1.0f
     */
    internal fun setBackGroundLevel(level: Float) {
        mWindow = (context as Activity).window
        val params = mWindow?.attributes
        params?.alpha = level
        mWindow?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        mWindow?.attributes = params
    }

    /**
     * 设置动画
     */
    private fun setAnimationStyle(animationStyle: Int) {
        popupWindow.animationStyle = animationStyle
    }

    /**
     * 设置Outside是否可点击
     * @param touchable 是否可点击
     */
    private fun setOutsideTouchable(touchable: Boolean) {
        //设置透明背景
        popupWindow.setBackgroundDrawable(ColorDrawable(0x00000000))
        //设置outside可点击
        popupWindow.isOutsideTouchable = touchable
        popupWindow.isFocusable = touchable
    }

    class PopupParams(context: Context) {
        var layoutResId: Int = 0//布局id
        var mContext: Context = context
        var mWidth: Int = 0
        var mHeight: Int = 0//弹窗的宽和高
        var isShowBg: Boolean = false
        var isShowAnim: Boolean = false
        var bgLevel: Float = 0.toFloat()//屏幕背景灰色程度
        var animationStyle: Int = 0//动画Id
        var mView: View? = null
        var isTouchable = true

        fun apply(controller: PopupController) {
            when {
                mView != null -> controller.setView(mView!!)
                layoutResId != 0 -> controller.setView(layoutResId)
                else -> throw IllegalArgumentException("PopupView's contentView is null")
            }
            controller.setWidthAndHeight(mWidth, mHeight)
            //设置outside可点击
            controller.setOutsideTouchable(isTouchable)
            if (isShowBg) {
                //设置背景
                controller.setBackGroundLevel(bgLevel)
            }
            if (isShowAnim) {
                controller.setAnimationStyle(animationStyle)
            }
        }
    }

}