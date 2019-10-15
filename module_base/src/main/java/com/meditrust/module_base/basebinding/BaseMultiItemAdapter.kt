package com.meditrust.module_base.basebinding

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/15
 * @desc:
 */
class BaseMultiItemAdapter() : RecyclerView.Adapter<BindingHolder>() {
    private var mDatas: MutableList<BindingMultiEntity>? = ArrayList()
    private var mOnItemClickListener: OnItemClickListener? = null
    private lateinit var mInflater: LayoutInflater
    private var mVariableId: Int = 0

    constructor(context: Context, mVariableId: Int) : this() {
        this.mVariableId = mVariableId
        this.mInflater = LayoutInflater.from(context)
    }

    fun getData(): List<BindingMultiEntity>? {
        return mDatas
    }

    fun setData(dataList: ArrayList<BindingMultiEntity>?) {
        mDatas = dataList
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    fun addData(position: Int, bean: BindingMultiEntity) {
        this.mDatas!!.add(position, bean)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return mDatas!![position].getItemType()
    }

    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): BindingHolder {
        val binding: ViewDataBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(@NonNull bindingHolder: BindingHolder, position: Int) {
        bindingHolder.getBinding().setVariable(mVariableId, mDatas!![position])
        bindingHolder.getBinding().executePendingBindings()
        bindingHolder.itemView.setOnClickListener {
            mOnItemClickListener!!.onItemClick(mDatas!![position], position)
        }
    }

    override fun getItemCount(): Int {
        return if (mDatas == null) {
            0
        } else mDatas!!.size
    }

    interface OnItemClickListener {
        fun onItemClick(bean: BindingMultiEntity, position: Int)
    }
}