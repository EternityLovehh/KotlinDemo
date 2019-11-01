package com.meditrust.module_drug.workroom.message

import android.app.Activity
import androidx.lifecycle.Observer
import com.meditrust.module_base.base.BaseListActivity
import com.meditrust.module_base.extensions.init
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.MsgAdapter
import com.meditrust.module_drug.databinding.ActivityMessageListBinding
import com.meditrust.module_drug.extensions.component
import com.meditrust.module_drug.model.MsgModel
import javax.inject.Inject

class MessageListActivity :
    BaseListActivity<MsgModel, ActivityMessageListBinding, MessageListViewModel>() {

    @Inject
    lateinit var msgAdapter: MsgAdapter

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_message_list
    }

    override fun initData() {
        setToolBar(mBinding.include.toolbar, mBinding.include.tvTitle, "系统消息")

        mBinding.rlMsg.init(msgAdapter)
        mViewModel.observeDataObserver(this,
            { msgAdapter.submitList(it) },
            { refreshFinished(it, mBinding.srlMsg, mBinding.emptyView) },
            { loadMoreFinished(it) })

        mBinding.srlMsg.setOnRefreshListener {
            mViewModel.invalidate()
        }

        mViewModel.msgLiveData.observe(this, Observer {
            mViewModel.updateMsg(it)
        })
        mViewModel.updateMsgLiveData.observe(this, Observer {
            if (it) setResult(Activity.RESULT_OK, intent)
        })

    }

    override fun initViewModel(): MessageListViewModel {
        return MessageListViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initJect() {
        component().inject(this)
    }

}
