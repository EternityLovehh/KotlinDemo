package com.meditrust.module_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    protected lateinit var mBinding: V
    protected lateinit var mViewModel: VM

    /**
     * 要想fragment依附于activity，请重写此方法，并返回true
     * （fragment xml根布局为，merge时）
     * @return
     */
    protected fun getAttachToParent(): Boolean {
        return false
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, initView(), container, getAttachToParent())
        mViewModel = initViewModel()
        mBinding.setVariable(initVariableId(), mViewModel)
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this

        initInject()

        initData()
        return return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel!!.onDestory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel!!.onDestory()
    }

    open fun initInject() {

    }

    abstract fun initView(): Int

    abstract fun initData()

    abstract fun initViewModel(): VM

    abstract fun initVariableId(): Int

    open fun isLazy() = false
}