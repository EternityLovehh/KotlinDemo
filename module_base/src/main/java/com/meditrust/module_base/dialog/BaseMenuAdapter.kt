package com.meditrust.module_base.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/26
 * @desc:
 */
abstract class BaseMenuAdapter : RecyclerView.Adapter<BindViewHolder>() {

    private var layoutRes by Delegates.notNull<Int>()
    private lateinit var datas: List<Any>
    private lateinit var adapterItemClickListener: OnAdapterItemClickListener
    private lateinit var dialog: CommonDialog

    protected abstract fun onBind(holder: BindViewHolder, position: Int, t: Any)

    fun BaseMenuAdapter(@LayoutRes layoutRes: Int, datas: List<Any>) {
        this.layoutRes = layoutRes
        this.datas = datas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindViewHolder {
        return BindViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))
    }

    override fun getItemCount(): Int {
        return if (datas?.isEmpty()!!) 0 else datas!!.size
    }

    override fun onBindViewHolder(holder: BindViewHolder, position: Int) {
        onBind(holder, position, datas!!.get(position))
        holder.itemView.setOnClickListener {
            adapterItemClickListener.onItemClick(
                holder,
                position,
                datas.get(position),
                dialog
            )
        }
    }

    fun setTDialog(tDialog: CommonDialog) {
        this.dialog = tDialog
    }

    interface OnAdapterItemClickListener {
        fun onItemClick(holder: BindViewHolder, position: Int, t: Any, tDialog: CommonDialog)
    }

    fun setOnAdapterItemClickListener(listener: OnAdapterItemClickListener) {
        this.adapterItemClickListener = listener
    }
}