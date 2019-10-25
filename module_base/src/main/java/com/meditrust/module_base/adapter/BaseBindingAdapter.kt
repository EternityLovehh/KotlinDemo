package com.meditrust.module_base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.meditrust.module_base.basebinding.BindingHolder


/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc: base databinding adapter
 */
open class BaseBindingAdapter<T> : RecyclerView.Adapter<BindingHolder> {

    private var mLayoutId: Int = 0
    private var mVariableId: Int = 0
    private var mDatas: List<T>? = null
    private var mOnItemClickListener: OnItemClickListener? = null

    constructor(mVariableId: Int, mLayoutId: Int) : super() {
        this.mVariableId = mVariableId
        this.mLayoutId = mLayoutId
    }

    fun setData(dataList: List<T>?) {
        mDatas = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), mLayoutId, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        holder.getBinding().setVariable(mVariableId, mDatas?.get(position))
        holder.getBinding().executePendingBindings()

        holder.itemView.setOnClickListener { mOnItemClickListener?.itemClick(position) }

    }

    override fun getItemCount(): Int {
        return if (null != mDatas) mDatas!!.size else 0
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun itemClick(position: Int)
    }
}
