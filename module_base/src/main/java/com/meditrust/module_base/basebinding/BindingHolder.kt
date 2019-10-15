package com.meditrust.module_base.basebinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc: databinding viewholder
 */
class BindingHolder<T : ViewDataBinding> : RecyclerView.ViewHolder {

    protected var mBinding: T

    constructor(binding: T) : super(binding.root) {
        this.mBinding = binding
    }

    fun getBinding(): T {
        return mBinding
    }
}