package com.meditrust.module_base.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.FragmentManager
import com.meditrust.module_base.listener.OnDialogBindViewListener
import com.meditrust.module_base.listener.OnDialogViewClickListener

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc: 通用弹框
 */

class CommonDialog : BaseDialogFragment {

    private var tController: DialogController<BaseMenuAdapter>
    private val KEY_TCONTROLLER = "CommonDialog"
    private var mBuilder: Builder

    constructor(builder: Builder) {
        this.mBuilder = builder
        tController = DialogController()
    }

    /**
     * 当设备旋转时,会重新调用onCreate,进行数据恢复
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            tController = savedInstanceState.getSerializable(KEY_TCONTROLLER) as DialogController<BaseMenuAdapter>
        }
    }

    /**
     * 进行数据保存
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_TCONTROLLER, tController)
        super.onSaveInstanceState(outState)
    }

    /**
     * 弹窗消失时回调方法
     */
    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val onDismissListener = tController.getOnDismissListener()
        onDismissListener?.onDismiss(dialog)
    }

    override fun getLayoutRes(): Int {
        return tController.getLayoutRes()
    }

    override fun bindView(view: View) {
        //控件点击事件处理
        val viewHolder = BindViewHolder(view, this)
        if (tController.getIds() != null && tController.getIds()!!.isNotEmpty()) {
            for (id in tController.getIds()!!) {
                viewHolder.addOnClickListenter(id)
            }
        }
        //回调方法获取到布局,进行处理
        tController.getOnBindViewListener()?.bindView(viewHolder)

    }

    override fun getDialogView(): View? {
        return tController.getDialogView()
    }

    override fun getGravity(): Int {
        return tController.getGravity()
    }

    override fun getDimAmount(): Float {
        return tController.getDimAmount()
    }

    override fun getDialogHeight(): Int {
        return tController.getHeight()
    }

    override fun getDialogWidth(): Int {
        return tController.getWidth()
    }

    override fun getFragmentTag(): String? {
        return tController.getTag()
    }

    fun getOnViewClickListener(): OnDialogViewClickListener? {
        return tController.getOnViewClickListener()
    }

    override fun isCancelableOutside(): Boolean {
        return tController.isCancelableOutside()
    }

    override fun getDialogAnimationRes(): Int {
        return tController.getDialogAnimationRes()
    }

    override fun getOnKeyListener(): DialogInterface.OnKeyListener? {
        return tController.getOnKeyListener()
    }

    fun show(): CommonDialog {
        try {
            val ft = tController.getFragmentManager().beginTransaction()
            ft.remove(this)
            ft.addToBackStack(null)
            ft.add(this, tController.getTag())
            ft.commitAllowingStateLoss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return this
    }

    class Builder {

        internal val params: DialogController.TParams<BaseMenuAdapter>

        constructor(fragmentManager: FragmentManager) {
            params = DialogController.TParams()
            params.mFragmentManager = fragmentManager
        }

        /**
         * 传入弹窗xmL布局文件
         *
         * @param layoutRes
         * @return
         */
        fun setLayoutRes(@LayoutRes layoutRes: Int): Builder {
            params.mLayoutRes = layoutRes
            return this
        }

        /**
         * 直接传入控件
         *
         * @param view
         * @return
         */
        fun setDialogView(view: View): Builder {
            params.mDialogView = view
            return this
        }

        /**
         * 设置弹窗宽度(单位:像素)
         *
         * @param widthPx
         * @return
         */
        fun setWidth(widthPx: Int): Builder {
            params.mWidth = widthPx
            return this
        }

        /**
         * 设置弹窗高度(px)
         *
         * @param heightPx
         * @return
         */
        fun setHeight(heightPx: Int): Builder {
            params.mHeight = heightPx
            return this
        }

        /**
         * 设置弹窗宽度是屏幕宽度的比例 0 -1
         */
        fun setScreenWidthAspect(context: Context, widthAspect: Float): Builder {
            params.mWidth = (getScreenWidth(context) * widthAspect).toInt()
            return this
        }

        /**
         * 设置弹窗高度是屏幕高度的比例 0 -1
         */
        fun setScreenHeightAspect(context: Context, heightAspect: Float): Builder {
            params.mHeight = (getScreenHeight(context) * heightAspect).toInt()
            return this
        }

        /**
         * 设置弹窗在屏幕中显示的位置
         *
         * @param gravity
         * @return
         */
        fun setGravity(gravity: Int): Builder {
            params.mGravity = gravity
            return this
        }

        /**
         * 设置弹窗在弹窗区域外是否可以取消
         *
         * @param cancel
         * @return
         */
        fun setCancelableOutside(cancel: Boolean): Builder {
            params.mIsCancelableOutside = cancel
            return this
        }

        /**
         * 弹窗dismiss时监听回调方法
         *
         * @param dismissListener
         * @return
         */
        fun setOnDismissListener(dismissListener: DialogInterface.OnDismissListener): Builder {
            params.mOnDismissListener = dismissListener
            return this
        }


        /**
         * 设置弹窗背景透明度(0-1f)
         *
         * @param dim
         * @return
         */
        fun setDimAmount(dim: Float): Builder {
            params.mDimAmount = dim
            return this
        }

        fun setTag(tag: String): Builder {
            params.mTag = tag
            return this
        }

        /**
         * 通过回调拿到弹窗布局控件对象
         *
         * @param listener
         * @return
         */
        fun setOnBindViewListener(listener: OnDialogBindViewListener): Builder {
            params.bindViewListener = listener
            return this
        }

        /**
         * 添加弹窗控件的点击事件
         *
         * @param ids 传入需要点击的控件id
         * @return
         */
        fun addOnClickListener(ids: IntArray): Builder {
            params.ids = ids
            return this
        }

        /**
         * 弹窗控件点击回调
         *
         * @param listener
         * @return
         */
        fun setOnViewClickListener(listener: OnDialogViewClickListener): Builder {
            params.mOnViewClickListener = listener
            return this
        }

        /**
         * 设置弹窗动画
         *
         * @param animationRes
         * @return
         */
        fun setDialogAnimationRes(animationRes: Int): Builder {
            params.mDialogAnimationRes = animationRes
            return this
        }

        /**
         * 监听弹窗后，返回键点击事件
         */
        fun setOnKeyListener(keyListener: DialogInterface.OnKeyListener): Builder {
            params.mKeyListener = keyListener
            return this
        }

        /**
         * 真正创建TDialog对象实例
         *
         * @return
         */
        fun create(): CommonDialog {
            val dialog = CommonDialog(this)
            //将数据从Buidler的DjParams中传递到DjDialog中
            params.apply(dialog.tController)
            return dialog
        }

    }


}