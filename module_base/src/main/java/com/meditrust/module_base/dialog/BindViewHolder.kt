package com.meditrust.module_base.dialog

import android.os.Build
import android.text.util.Linkify
import android.util.SparseArray
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.*
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc:
 */
class BindViewHolder : RecyclerView.ViewHolder {
    private var bindView: View? = null
    private var views: SparseArray<View>? = null
    private var dialog: CommonDialog? = null

    constructor(view: View) : super(view) {
        this.bindView = view
        this.views = SparseArray()
    }

    constructor(view: View, dialog: CommonDialog) : this(view) {
        this.bindView = view
        this.dialog = dialog
        views = SparseArray()
    }

    fun <T : View> getView(@IdRes viewId: Int): T {
        var view: View? = views?.get(viewId)
        if (view == null) {
            view = bindView?.findViewById(viewId)
            views?.put(viewId, view)
        }
        return view as T
    }

    fun addOnClickListenter(@IdRes viewId: Int): BindViewHolder {
        val view: View = getView(viewId)
        view?.let {
            if (!it.isClickable) it.isClickable = true

            it.setOnClickListener {
                dialog?.getOnViewClickListener()?.onDialogViewClick(this, view, dialog!!)
            }
        }
        return this
    }

    fun setText(@IdRes viewId: Int, value: String): BindViewHolder {
        val view: TextView = getView(viewId)
        view.text = value
        return this
    }

    fun setText(@IdRes viewId: Int, @StringRes strId: Int): BindViewHolder {
        val view = getView<TextView>(viewId)
        view.setText(strId)
        return this
    }

    fun setTextColor(@IdRes viewId: Int, @ColorInt textColor: Int): BindViewHolder {
        val view = getView<TextView>(viewId)
        view.setTextColor(textColor)
        return this
    }

    fun setImageResource(@IdRes viewId: Int, @DrawableRes imageResId: Int): BindViewHolder {
        val view = getView<ImageView>(viewId)
        view.setImageResource(imageResId)
        return this
    }

    fun setBackgroundColor(@IdRes viewId: Int, @ColorInt color: Int): BindViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundColor(color)
        return this
    }

    fun setBackgroundRes(@IdRes viewId: Int, @DrawableRes backgroundRes: Int): BindViewHolder {
        val view = getView<View>(viewId)
        view.setBackgroundResource(backgroundRes)
        return this
    }

    fun setAlpha(@IdRes viewId: Int, value: Float): BindViewHolder {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView<View>(viewId).alpha = value
        } else {
            // Pre-honeycomb hack to set Alpha value
            val alpha = AlphaAnimation(value, value)
            alpha.duration = 0
            alpha.fillAfter = true
            getView<View>(viewId).startAnimation(alpha)
        }
        return this
    }

    fun setGone(@IdRes viewId: Int, visible: Boolean): BindViewHolder {
        val view = getView<View>(viewId)
        view.visibility = if (visible) View.VISIBLE else View.GONE
        return this
    }

    fun setVisible(@IdRes viewId: Int, visible: Boolean): BindViewHolder {
        val view = getView<View>(viewId)
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        return this
    }

    fun setVisibility(@IdRes viewId: Int, visible: Int): BindViewHolder {
        getView<View>(viewId).visibility = visible
        return this
    }

    fun linkify(@IdRes viewId: Int): BindViewHolder {
        val view = getView<TextView>(viewId)
        Linkify.addLinks(view, Linkify.ALL)
        return this
    }

    fun setProgress(@IdRes viewId: Int, progress: Int): BindViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.progress = progress
        return this
    }

    fun setProgress(@IdRes viewId: Int, progress: Int, max: Int): BindViewHolder {
        val view = getView<ProgressBar>(viewId)
        view.max = max
        view.progress = progress
        return this
    }

    fun setOnItemLongClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemLongClickListener): BindViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.onItemLongClickListener = listener
        return this
    }

    fun setOnItemSelectedClickListener(@IdRes viewId: Int, listener: AdapterView.OnItemSelectedListener): BindViewHolder {
        val view = getView<AdapterView<*>>(viewId)
        view.onItemSelectedListener = listener
        return this
    }

    fun setOnCheckedChangeListener(@IdRes viewId: Int, listener: CompoundButton.OnCheckedChangeListener): BindViewHolder {
        val view = getView<CompoundButton>(viewId)
        view.setOnCheckedChangeListener(listener)
        return this
    }

    fun setTag(@IdRes viewId: Int, tag: Any): BindViewHolder {
        val view = getView<View>(viewId)
        view.tag = tag
        return this
    }

    fun setTag(@IdRes viewId: Int, key: Int, tag: Any): BindViewHolder {
        val view = getView<View>(viewId)
        view.setTag(key, tag)
        return this
    }

    fun setChecked(@IdRes viewId: Int, checked: Boolean): BindViewHolder {
        val view = getView<View>(viewId)
        // View unable cast to Checkable
        if (view is Checkable) {
            (view as Checkable).isChecked = checked
        }
        return this
    }

}