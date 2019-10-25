package com.meditrust.module_drug.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.meditrust.module_drug.databinding.ItemDrugListBinding
import com.meditrust.module_drug.model.DrugModel
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/15
 * @desc:
 */
class AllDrugAdapter @Inject constructor() :
    PagedListAdapter<DrugModel, AllDrugAdapter.ViewHolder>(DrugDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDrugListBinding.inflate(
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
        private val binding: ItemDrugListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DrugModel) {
            binding.apply {
                bean = item
                executePendingBindings()
            }
        }
    }

    private class DrugDiffCallback : DiffUtil.ItemCallback<DrugModel>() {
        override fun areContentsTheSame(oldItem: DrugModel, newItem: DrugModel): Boolean {
            return oldItem.bId == newItem.bId
        }

        override fun areItemsTheSame(oldItem: DrugModel, newItem: DrugModel): Boolean {
            return oldItem == newItem
        }
    }
}