package com.meditrust.moudle_user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meditrust.module_base.model.GuideModel
import com.meditrust.moudle_user.R

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/19
 * @desc:
 */
class GuidePageAdapter() : RecyclerView.Adapter<GuidePageAdapter.GuideViewHolder>() {

    private lateinit var mContext: Context
    private lateinit var mList: ArrayList<GuideModel>
    private lateinit var mInflater: LayoutInflater

    constructor(context: Context, mDataList: ArrayList<GuideModel>) : this() {
        mContext = context
        mList = mDataList
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        val view = mInflater.inflate(R.layout.layout_guide_page, parent, false)
        return GuideViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mList != null) mList.size else 0
    }

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        holder.imageView.setImageResource(mList.get(position).imgId)
        holder.textView.setText(mList.get(position).text)
    }

    inner class GuideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.tv_guide_detail)
        var imageView: ImageView = view.findViewById(R.id.iv_guide)
    }
}