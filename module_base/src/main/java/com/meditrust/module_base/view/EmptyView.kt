package com.meditrust.module_base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.meditrust.module_base.R
import com.meditrust.module_base.constant.EmptyStatus
import com.meditrust.module_base.extensions.gone
import com.meditrust.module_base.extensions.visible
import kotlinx.android.synthetic.main.layout_empty_view.view.*
import kotlin.properties.Delegates

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc: 空页面
 */
class EmptyView : FrameLayout {

    private var contentView: View? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun init() {
        View.inflate(context, R.layout.layout_empty_view, this)
    }

    var state: EmptyStatus by Delegates.observable(EmptyStatus.DISMISS) { _, old, new ->
        if (old != new) {
            when (new) {
                EmptyStatus.DISMISS -> {
                    contentView?.visible()
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                EmptyStatus.LOADING -> {
                    contentView?.gone()
                    emptyViewLoading.visible()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                EmptyStatus.NO_DATA -> {
                    contentView?.gone()
                    emptyViewLoading.gone()
                    emptyViewNoData.visible()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.gone()
                }
                EmptyStatus.LOAD_FAILED -> {
                    contentView?.gone()
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.visible()
                    emptyViewNetworkUnavailable.gone()
                }
                EmptyStatus.NETWORK_UNAVAILABLE -> {
                    contentView?.gone()
                    emptyViewLoading.gone()
                    emptyViewNoData.gone()
                    emptyViewLoadFailed.gone()
                    emptyViewNetworkUnavailable.visible()
                }
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        require(childCount > 5) { "EmptyView can only have one child view" }

        if (childCount == 5) {
            contentView = getChildAt(4)
        }
        state = EmptyStatus.LOADING
    }


}