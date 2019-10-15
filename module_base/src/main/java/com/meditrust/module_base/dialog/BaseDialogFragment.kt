package com.meditrust.module_base.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc: dialogfragment
 */
abstract class BaseDialogFragment : DialogFragment() {
    companion object {
        val DEFAULT_DIMAMOUNT = 0.2f
        val TAG = "BaseDialogFragment"

        //获取设备屏幕宽度
        fun getScreenWidth(context: Context): Int {
            return context.resources.displayMetrics.widthPixels
        }

        //获取设备屏幕高度
        open fun getScreenHeight(context: Context): Int {
            return context.resources.displayMetrics.heightPixels
        }
    }

    protected abstract fun getLayoutRes(): Int

    protected abstract fun bindView(view: View)

    protected abstract fun getDialogView(): View?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.apply {
            this.requestWindowFeature(Window.FEATURE_NO_TITLE)
            this.setCanceledOnTouchOutside(isCancelable)
            this.window?.setWindowAnimations(getDialogAnimationRes())
            this.setOnKeyListener(getOnKeyListener())
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view: View? = null
        if (getLayoutRes() > 0) {
            view = inflater.inflate(getLayoutRes(), container, false)
        }
        if (getDialogView() != null) {
            view = this!!.getDialogView()!!
        }
        bindView(view!!)
        return view
    }

    override fun onStart() {
        super.onStart()
        val window = dialog!!.window
        if (window != null) {
            //设置窗体背景色透明
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            //设置宽高
            val layoutParams = window.attributes
            if (getDialogWidth() > 0) {
                layoutParams.width = getDialogWidth()
            } else {
                layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT
            }
            if (getDialogHeight() > 0) {
                layoutParams.height = getDialogHeight()
            } else {
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            //透明度
            layoutParams.dimAmount = getDimAmount()
            //位置
            layoutParams.gravity = getGravity()
            window.attributes = layoutParams
        }
    }

    protected open fun getOnKeyListener(): DialogInterface.OnKeyListener? {
        return null
    }

    //默认弹窗位置为中心
    open fun getGravity(): Int {
        return Gravity.CENTER
    }

    //默认宽高为包裹内容
    open fun getDialogHeight(): Int {
        return (getScreenHeight(activity!!.applicationContext) * 0.2).toInt()
    }

    open fun getDialogWidth(): Int {
        return (getScreenWidth(activity!!.applicationContext) * 0.8).toInt()
    }

    //默认透明度为0.2
    open fun getDimAmount(): Float {
        return DEFAULT_DIMAMOUNT
    }

    open fun getFragmentTag(): String? {
        return TAG
    }

    fun show(fragmentManager: FragmentManager) {
        show(fragmentManager, getFragmentTag())
    }

    protected open fun isCancelableOutside(): Boolean {
        return true
    }

    //获取弹窗显示动画,子类实现
    protected open fun getDialogAnimationRes(): Int {
        return 0
    }
}