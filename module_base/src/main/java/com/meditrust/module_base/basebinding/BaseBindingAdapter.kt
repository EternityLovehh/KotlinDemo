package com.meditrust.module_base.basebinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc: base databinding adapter
 */
open class BaseBindingAdapter<T, B : ViewDataBinding>() : RecyclerView.Adapter<BindingHolder<B>>() {

    private lateinit var mInflater: LayoutInflater
    private var mLayoutId: Int = 0
    private var mVariableId: Int = 0
    private var mDatas: ArrayList<T>? = null

    constructor(context: Context, mVariableId: Int, mLayoutId: Int) : this() {
        this.mVariableId = mVariableId
        this.mLayoutId = mLayoutId
        this.mInflater = LayoutInflater.from(context)
    }

    fun setData(dataList: ArrayList<T>?) {
        mDatas = dataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<B> {
        val binding: B = DataBindingUtil.inflate(mInflater, mLayoutId, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder<B>, position: Int) {
        holder.getBinding().setVariable(mVariableId, mDatas?.get(position))
        holder.getBinding().executePendingBindings()

    }

    override fun getItemCount(): Int {
        return if (null != mDatas) mDatas!!.size else 0
    }
}
