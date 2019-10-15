package com.meditrust.module_base.dialog

import android.content.DialogInterface
import android.os.Parcel
import android.os.Parcelable
import android.view.Gravity
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.meditrust.module_base.R
import com.meditrust.module_base.listener.OnDialogBindViewListener
import com.meditrust.module_base.listener.OnDialogViewClickListener

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc:
 */
class DialogController<A : BaseMenuAdapter>() : Parcelable {

    private lateinit var fragmentManager: FragmentManager
    private var layoutRes: Int = 0
    private var height: Int = 0
    private var width: Int = 0
    private var dimAmount: Float = 0.2F
    private var gravity = Gravity.CENTER
    private var tag: String? = null
    private var mIds: IntArray? = null
    private var isCancelableOutside: Boolean = true
    private var onViewClickListener: OnDialogViewClickListener? = null
    private var onBindViewListener: OnDialogBindViewListener? = null
    private var adapter: A? = null
    private var adapterItemClickListener: BaseMenuAdapter.OnAdapterItemClickListener? = null
    private var orientation: Int = 0
    private var dialogAnimationRes: Int = 0
    private var dialogView: View? = null
    private var onDismissListener: DialogInterface.OnDismissListener? = null
    private var onKeyListener: DialogInterface.OnKeyListener? = null

    constructor(parcel: Parcel) : this() {
        layoutRes = parcel.readInt()
        height = parcel.readInt()
        width = parcel.readInt()
        dimAmount = parcel.readFloat()
        gravity = parcel.readInt()
        tag = parcel.readString().toString()
        mIds = parcel.createIntArray()
        isCancelableOutside = parcel.readByte() != 0.toByte()
        orientation = parcel.readInt()
        dialogAnimationRes = parcel.readInt()
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(layoutRes)
        parcel.writeInt(height)
        parcel.writeInt(width)
        parcel.writeFloat(dimAmount)
        parcel.writeInt(gravity)
        parcel.writeString(tag)
        parcel.writeIntArray(mIds)
        parcel.writeByte(if (isCancelableOutside) 1 else 0)
        parcel.writeInt(orientation)
        parcel.writeInt(dialogAnimationRes)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DialogController<BaseMenuAdapter>> {
        override fun createFromParcel(parcel: Parcel): DialogController<BaseMenuAdapter> {
            return DialogController(parcel)
        }

        override fun newArray(size: Int): Array<DialogController<BaseMenuAdapter>?> {
            return arrayOfNulls(size)
        }
    }


    fun getFragmentManager(): FragmentManager {
        return fragmentManager
    }

    fun getLayoutRes(): Int {
        return layoutRes
    }

    fun setLayoutRes(layoutRes: Int) {
        this.layoutRes = layoutRes
    }

    fun getHeight(): Int {
        return height
    }

    fun getWidth(): Int {
        return width
    }

    fun setWidth(mWidth: Int) {
        this.width = mWidth
    }

    fun getDimAmount(): Float {
        return dimAmount
    }

    fun getGravity(): Int {
        return gravity
    }

    fun getTag(): String? {
        return tag
    }

    fun getIds(): IntArray? {
        return mIds
    }

    fun isCancelableOutside(): Boolean {
        return isCancelableOutside
    }

    fun getOnViewClickListener(): OnDialogViewClickListener? {
        return onViewClickListener
    }

    fun getOrientation(): Int {
        return orientation
    }

    fun getOnBindViewListener(): OnDialogBindViewListener? {
        return onBindViewListener
    }

    fun getOnDismissListener(): DialogInterface.OnDismissListener? {
        return onDismissListener
    }

    fun getOnKeyListener(): DialogInterface.OnKeyListener? {
        return onKeyListener
    }

    fun getDialogView(): View? {
        return dialogView
    }

    /**
     * 列表
     */
    fun getAdapter(): A? {
        return adapter
    }

    fun setAdapter(adapter: A) {
        this.adapter = adapter
    }

    fun getAdapterItemClickListener(): BaseMenuAdapter.OnAdapterItemClickListener? {
        return adapterItemClickListener
    }

    fun setAdapterItemClickListener(adapterItemClickListener: BaseMenuAdapter.OnAdapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener
    }

    fun getDialogAnimationRes(): Int {
        return dialogAnimationRes
    }

    class TParams<A : BaseMenuAdapter> {
        lateinit var mFragmentManager: FragmentManager
        var mLayoutRes: Int = 0
        var mWidth: Int = 0
        var mHeight: Int = 0
        var mDimAmount = 0.2f
        var mGravity = Gravity.CENTER
        var mTag = "CommonDialog"
        var ids: IntArray? = intArrayOf(2)
        var mIsCancelableOutside = true
        var mOnViewClickListener: OnDialogViewClickListener? = null
        var bindViewListener: OnDialogBindViewListener? = null
        var mDialogAnimationRes = 0//弹窗动画
        //列表
        lateinit var adapter: A
        lateinit var adapterItemClickListener: BaseMenuAdapter.OnAdapterItemClickListener
        var listLayoutRes: Int = 0
        var orientation = LinearLayoutManager.VERTICAL//默认RecyclerView的列表方向为垂直方向
        var mDialogView: View? = null//直接使用传入进来的View,而不需要通过解析Xml
        var mOnDismissListener: DialogInterface.OnDismissListener? = null
        var mKeyListener: DialogInterface.OnKeyListener? = null

        fun apply(dialogController: DialogController<A>) {

            dialogController.apply {
                this.fragmentManager = mFragmentManager
                if (mLayoutRes > 0) this.layoutRes = mLayoutRes
                if (mDialogView != null) this.dialogView = mDialogView
                if (mWidth > 0) this.width = mWidth
                if (mHeight > 0) this.height = mHeight
                this.dimAmount = mDimAmount
                this.gravity = mGravity
                this.tag = mTag
                this.mIds = ids
                this.isCancelableOutside = mIsCancelableOutside
                this.onViewClickListener = mOnViewClickListener
                this.onBindViewListener = bindViewListener
                this.onDismissListener = mOnDismissListener
                this.onKeyListener = onKeyListener
                adapter?.let {
                    this.adapter = adapter
                    if (listLayoutRes <= 0) this.layoutRes = R.layout.dialog_recycler else this.layoutRes =
                        listLayoutRes
                }
                this.orientation = orientation
                adapterItemClickListener?.let { this.adapterItemClickListener = adapterItemClickListener }

                if (this.width <= 0 && this.height <= 0) {
                    this.width = 600
                    this.height = 400
                }
            }
        }
    }

}