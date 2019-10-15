package com.meditrust.module_base.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.meditrust.module_base.basebinding.BindingHolder

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc:
 */
abstract class BasePagedAdapter<T : BaseItem, B : ViewDataBinding>() :
    PagedListAdapter<T, BindingHolder<B>>(Diff<T>()) {

    private lateinit var mInflater: LayoutInflater
    private var mLayoutId: Int = 0
    private var mVariableId: Int = 0
    private var mDatas: ArrayList<T>? = null

    constructor(context: Context, mVariableId: Int, mLayoutId: Int) : this() {
        this.mVariableId = mVariableId
        this.mLayoutId = mLayoutId
        this.mInflater = LayoutInflater.from(context)
    }

    var itemClickListener: ((View, T, Int) -> Unit)? = null

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

    fun changeItem(position: Int) {
        if (position in 0 until itemCount) {
            notifyItemChanged(position)
        }
    }

    fun changeItem(position: Int, payload: Any?) {
        if (position in 0 until itemCount) {
            notifyItemChanged(position, payload)
        }
    }


    class Diff<T : BaseItem> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(item: T, item1: T) = item.bId == item1.bId

        override fun areContentsTheSame(item: T, item1: T) = item == item1
    }
}