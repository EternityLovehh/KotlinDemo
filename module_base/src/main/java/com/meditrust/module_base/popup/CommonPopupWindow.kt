package com.meditrust.module_base.popup

import android.content.Context
import android.view.View
import android.widget.PopupWindow
import com.meditrust.module_base.extensions.gone
import com.meditrust.module_base.extensions.visible


/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/17
 * @desc:
 */
class CommonPopupWindow : PopupWindow {

    var controller: PopupController
    private var rlMask: View? = null

    constructor(context: Context) : super(context) {
        controller = PopupController(context, this)
    }

    override fun getWidth(): Int {
        return controller.mPopupView?.measuredWidth!!
    }

    override fun getHeight(): Int {
        return controller.mPopupView?.measuredHeight!!
    }

    override fun dismiss() {
        rlMask?.gone()
        super.dismiss()
    }

    fun setMaskView(view: View) {
        rlMask = view
    }

    fun showPopupWindow(view: View) {
        if (!isShowing) {
            showAsDropDown(view)
            rlMask?.visible()
        } else {
            dismiss()
        }
    }

    class Builder(context: Context) {
        private val params: PopupController.PopupParams = PopupController.PopupParams(context)
        var listener: ViewInterface? = null
        /**
         * 设置布局
         */
        fun setView(layoutResId: Int): Builder {
            params.mView = null
            params.layoutResId = layoutResId
            return this
        }

        /**
         * 设置布局
         */
        fun setView(view: View): Builder {
            params.mView = view
            params.layoutResId = 0
            return this
        }

        /**
         * 设置子View
         *
         * @param listener ViewInterface
         * @return Builder
         */
        fun setViewOnclickListener(listener: ViewInterface): Builder {
            this.listener = listener
            return this
        }

        /**
         * 设置宽高
         */
        fun setWidthAndHeight(width: Int, height: Int): Builder {
            params.mWidth = width
            params.mHeight = height
            return this
        }

        /**
         * 设置背景灰度
         */
        fun setBackGroundLevel(level: Float): Builder {
            params.isShowBg = true
            params.bgLevel = level
            return this
        }

        /**
         * 是否可点击外面消失
         */
        fun setOutSideTouchable(touchable: Boolean): Builder {
            params.isTouchable = touchable
            return this
        }

        /**
         * 设置动画
         */
        fun setAnimationStyle(animationStyle: Int): Builder {
            params.isShowAnim = true
            params.animationStyle = animationStyle
            return this
        }

        fun create(): CommonPopupWindow {
            val popupWindow = CommonPopupWindow(params.mContext)
            params.apply(popupWindow.controller)
            if (params.layoutResId != 0 && listener != null) {
                listener?.getChildView(popupWindow.controller.mPopupView!!, params.layoutResId)
            }
            val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            val heightMeasureSpec =
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            popupWindow.controller.mPopupView?.measure(widthMeasureSpec, heightMeasureSpec)
            return popupWindow
        }

    }


    interface ViewInterface {
        fun getChildView(view: View, layoutResId: Int)
    }
}