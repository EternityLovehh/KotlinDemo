package com.meditrust.module_base.basebinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc: databinding viewholder
 */
class BindingHolder : RecyclerView.ViewHolder {

    protected var mBinding: ViewDataBinding

    constructor(binding: ViewDataBinding) : super(binding.root) {
        this.mBinding = binding
    }

    fun getBinding(): ViewDataBinding {
        return mBinding
    }
}