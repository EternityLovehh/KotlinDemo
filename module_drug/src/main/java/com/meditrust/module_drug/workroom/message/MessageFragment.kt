package com.meditrust.module_drug.workroom.message

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.meditrust.module_base.base.BaseFragment
import com.meditrust.module_base.constant.EmptyStatus
import com.meditrust.module_base.constant.RefreshResult
import com.meditrust.module_base.extensions.gone
import com.meditrust.module_base.extensions.visible
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.databinding.FragmentMessageBinding
import org.jetbrains.anko.support.v4.startActivityForResult

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/31
 * @desc:
 */
class MessageFragment : BaseFragment<FragmentMessageBinding, MessageViewModel>() {

    companion object {
        const val MSG_CODE = 100
    }

    override fun initView(): Int {
        return R.layout.fragment_message
    }

    override fun initData() {

        mViewModel.queryMsg()

        mViewModel.loadLiveData?.observe(this, Observer<RefreshResult> {
            mBinding.emptyView?.apply {
                state = when (it) {
                    RefreshResult.SUCCEED -> {
                        mBinding.llMsg.visible()
                        EmptyStatus.DISMISS
                    }
                    RefreshResult.FAILED -> {
                        mBinding.llMsg.gone()
                        EmptyStatus.LOAD_FAILED
                    }
                    else -> {
                        mBinding.llMsg.gone()
                        EmptyStatus.NO_DATA
                    }
                }
            }
        })

        mViewModel.mMsgLiveData.observe(this, Observer {
            if (it?.quantity == 0) {
                mBinding.tvMsgCount.gone()
            } else {
                mBinding.tvMsgCount.visible()
            }
            mBinding.bean = it

            LiveEventBus.get("msg_count").post(it?.quantity)
        })

        mBinding.llMsg.setOnClickListener { startActivityForResult<MessageListActivity>(MSG_CODE) }

    }

    override fun initViewModel(): MessageViewModel {
        return MessageViewModel(activity!!.application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == MSG_CODE) {
            mBinding.tvMsgCount.gone()
        }
    }

}