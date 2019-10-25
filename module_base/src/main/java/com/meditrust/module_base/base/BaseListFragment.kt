package com.meditrust.module_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.meditrust.module_base.constant.EmptyStatus
import com.meditrust.module_base.constant.RefreshResult
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_base.view.EmptyView

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc:
 */
abstract class BaseListFragment<T : BaseItem, V : ViewDataBinding, VM : BaseListViewModel<T>> : Fragment() {

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
        return mBinding.root
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

    open fun itemClicked(view: View, item: T, position: Int) {

    }

    fun refreshFinished(
        result: RefreshResult,
        refreshLayout: SwipeRefreshLayout?,
        emptyView: EmptyView
    ) {
        refreshLayout?.isRefreshing = false
        emptyView?.apply {
            state = when (result) {
                RefreshResult.SUCCEED -> EmptyStatus.DISMISS
                RefreshResult.FAILED -> EmptyStatus.LOAD_FAILED
                RefreshResult.NO_DATA -> EmptyStatus.NO_DATA
                RefreshResult.NO_MORE -> {
                    EmptyStatus.DISMISS
                }
            }
        }
    }

    fun loadMoreFinished(result: RefreshResult) {
        when (result) {
            RefreshResult.SUCCEED -> {
            }
            RefreshResult.FAILED -> {
            }
            RefreshResult.NO_MORE -> ToastUtils.showToast("全部加载完成")
        }
    }

}