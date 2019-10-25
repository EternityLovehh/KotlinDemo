package com.meditrust.module_drug.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.meditrust.module_drug.databinding.ItemWelfateBinding
import com.meditrust.module_drug.model.RecruitModel
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/25
 * @desc:
 */
class WelfareAdapter @Inject constructor() :
    PagedListAdapter<RecruitModel, WelfareAdapter.ViewHolder>(WelfareDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWelfateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
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
        private val binding: ItemWelfateBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: RecruitModel) {
            binding.apply {
                bean = item
                executePendingBindings()
            }
        }
    }

    private class WelfareDiffCallback : DiffUtil.ItemCallback<RecruitModel>() {
        override fun areContentsTheSame(oldItem: RecruitModel, newItem: RecruitModel): Boolean {
            return oldItem.bId == newItem.bId
        }

        override fun areItemsTheSame(oldItem: RecruitModel, newItem: RecruitModel): Boolean {
            return oldItem == newItem
        }
    }
}