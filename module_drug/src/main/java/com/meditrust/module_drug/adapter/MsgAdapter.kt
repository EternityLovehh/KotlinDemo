package com.meditrust.module_drug.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.meditrust.module_drug.databinding.ItemMsgBinding
import com.meditrust.module_drug.model.MsgModel
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/11/1
 * @desc:
 */
class MsgAdapter @Inject constructor() :
    PagedListAdapter<MsgModel, MsgAdapter.ViewHolder>(MsgDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMsgBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = getItem(position) ?: return
        holder.apply {
            bind(data)
            itemView.tag = data
        }
    }


    class ViewHolder(
        private val binding: ItemMsgBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MsgModel) {
            binding.apply {
                bean = item
                executePendingBindings()
            }
        }
    }

    private class MsgDiffCallback : DiffUtil.ItemCallback<MsgModel>() {
        override fun areItemsTheSame(oldItem: MsgModel, newItem: MsgModel): Boolean {
            return oldItem.bId == newItem.bId
        }

        override fun areContentsTheSame(oldItem: MsgModel, newItem: MsgModel): Boolean {
            return oldItem == newItem
        }

    }
}