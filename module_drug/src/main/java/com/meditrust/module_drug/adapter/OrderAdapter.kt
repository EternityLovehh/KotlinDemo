package com.meditrust.module_drug.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.gone
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.visible
import com.meditrust.module_base.model.BaseItem
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_drug.R
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.bean.ConfirmOrderBean
import com.meditrust.module_drug.common.OrderStatus
import com.meditrust.module_drug.databinding.ItemOrderContentBinding
import com.meditrust.module_drug.databinding.ItemOrderFooterBinding
import com.meditrust.module_drug.databinding.ItemOrderHeaderBinding
import com.meditrust.module_drug.model.HomedeliveryOrderDetailRespBean
import com.meditrust.module_drug.model.OrderFootModel
import com.meditrust.module_drug.model.ResultsBean
import com.meditrust.module_drug.upload.UploadIdCardActivity
import io.reactivex.rxkotlin.subscribeBy
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import javax.inject.Inject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/14
 * @desc:
 */
class OrderAdapter @Inject constructor() :
    PagedListAdapter<BaseItem, RecyclerView.ViewHolder>(OrderDiffCallback()) {
    companion object {
        const val TYPE_HEADER = 100
        const val TYPE_CONTENT = 200
        const val TYPE_FOOTER = 300
    }

    var itemClickListener: ((View, BaseItem, Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_HEADER -> return HeaderViewHolder(
                ItemOrderHeaderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            TYPE_CONTENT -> return ContentViewHolder(
                ItemOrderContentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> return FooterViewHolder(
                ItemOrderFooterBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        itemClickListener?.apply {
            holder.itemView.setOnClickListener {
                this(
                    holder.itemView,
                    getItem(position)!!,
                    position
                )
            }
        }

        val data = getItem(position) ?: return
        when (holder) {
            is HeaderViewHolder -> holder.apply {
                bind(data as ResultsBean)
                itemView.tag = data
            }
            is ContentViewHolder -> holder.apply {
                bind(data as HomedeliveryOrderDetailRespBean)
                itemView.tag = data
            }
            is FooterViewHolder -> holder.apply {
                bind(data as OrderFootModel)
                itemView.tag = data

                holder.getBinding().tvInvoiceType.setOnClickListener {
                    val intent = Intent(it.context, UploadIdCardActivity::class.java)
                    intent.putExtra(Const.UPLOAD_TYPE, Const.INVOICE_INFO)
                    intent.putExtra(Const.ORDER_NUM, data.resultsBean?.orderNo)
                    it.context?.startActivity(intent)
                }

                holder.getBinding().tvOrderDeal.setOnClickListener {
                    orderCancelClick(
                        it.context,
                        data.resultsBean!!,
                        data.status!!
                    )
                }
                holder.getBinding().tvOrderConfirm.setOnClickListener {
                    orderConfirmClick(
                        it.context,
                        data,
                        data.status!!
                    )
                }
            }
        }
    }

    private fun completeOrder(orderNo: String?) {
        //订单完成
        val jsonObject = JSONObject()
        try {
            jsonObject.put("orderNo", orderNo)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody =
            jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.successOrder(requestBody)
            .io_main()
            .subscribeBy {
                ToastUtils.showToast(it.message)
            }
    }

    private fun auditOrder(data: OrderFootModel) {
        val confirmOrderBean = ConfirmOrderBean()
        val deliveryList = ArrayList<ConfirmOrderBean.HomedeliveryOrderConfirmDetailReqListBean>()
        confirmOrderBean.apply {
            orderNo = data.resultsBean?.orderNo
            remark = data.resultsBean?.remark
            for (respBean in data.homedeliveryOrderDetailResp!!) {
                val delivertListBean = ConfirmOrderBean.HomedeliveryOrderConfirmDetailReqListBean()
                delivertListBean.id = respBean.id.toString()
                if (0 == respBean.commodityPrice.toInt()) {
                    ToastUtils.showToast("价格不能为0")
                    return
                }
                delivertListBean.price = respBean.commodityPrice.toString()
                delivertListBean.quantity = respBean.quantity.toString()
                deliveryList.add(delivertListBean)
            }
            confirmOrderBean.homedeliveryOrderConfirmDetailReqList = deliveryList
        }
        val requestBody = GsonBuilder().setPrettyPrinting().create().toJson(confirmOrderBean)
            .toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.auditOrder(requestBody)
            .io_main()
            .subscribeBy {
                ToastUtils.showToast(it.message)
            }
    }

    private fun deveryDrug(context: Context, resultsBean: ResultsBean?) {
//        val intent = Intent(context, SenderDrugActivity::class.java)
//        intent.putExtra(Const.ORDER_INFO, resultsBean)
//        context.startActivity(intent)
    }

    private fun orderConfirmClick(context: Context, data: OrderFootModel, status: String) {
        when (status) {
            OrderStatus.UN_AUDIT_ORDER -> {
                //通过审核
                auditOrder(data)
            }
            OrderStatus.UN_TAKE_DRUG,
            OrderStatus.HAVE_DELIVERY_ORDER -> {
                completeOrder(data.resultsBean?.orderNo)
            }
            OrderStatus.UN_DELIVERY_ORDER,
            OrderStatus.UN_DELIVERY_AND_PAY -> {
                //去发货
                deveryDrug(context, data.resultsBean)
            }
            OrderStatus.UN_PAY,
            OrderStatus.HAVE_DELIVERY_AND_PAY -> {
                //收款
                payMoney(context, data.resultsBean)
            }
        }
    }

    private fun payMoney(context: Context, resultsBean: ResultsBean?) {
//        val intent = Intent(context, PayActivity::class.java)
//        intent.putExtra(Const.ORDER_NUM, resultsBean?.orderNo)
//        intent.putExtra(Const.USER_ID, resultsBean?.userId)
//        context.startActivity(intent)
    }


    private fun orderCancelClick(context: Context, resultsBean: ResultsBean, status: String) {
        when (status) {
            OrderStatus.UN_AUDIT_ORDER -> {
                //驳回
//                val intent = Intent(mContext, RefushActivity::class.java)
//                intent.putExtra(Const.ORDER_NUM, resultsBean.orderNo)
//                mContext?.startActivity(intent)
                Log.e("ad", "驳回")
            }
            OrderStatus.HAVE_DELIVERY_ORDER -> {
                //查看物流
//                val intent1 = Intent(mContext, LogisticActivity::class.java)
//                intent1.putExtra(Const.ORDER_INFO, resultsBean)
//                mContext?.startActivity(intent1)
                Log.e("ad", "查看物流")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            currentList!![position] is ResultsBean -> TYPE_HEADER
            currentList!![position] is HomedeliveryOrderDetailRespBean -> TYPE_CONTENT
            else -> TYPE_FOOTER
        }
    }


    class HeaderViewHolder(
        private val binding: ItemOrderHeaderBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsBean) {
            binding.apply {
                bean = item
                executePendingBindings()
            }
        }
    }

    class ContentViewHolder(
        private val binding: ItemOrderContentBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HomedeliveryOrderDetailRespBean) {
            binding.apply {
                bean = item
                executePendingBindings()
            }
        }
    }

    class FooterViewHolder(
        private val binding: ItemOrderFooterBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderFootModel) {
            binding.apply {
                bean = item
                setOrderButton(item.status!!, tvOrderDeal, tvOrderConfirm)
                executePendingBindings()
            }
        }

        fun getBinding(): ItemOrderFooterBinding {
            return binding
        }

        private fun setOrderButton(
            status: String,
            tvOrderDeal: TextView,
            tvOrderConfirm: TextView
        ) {
            when (status) {
                OrderStatus.UN_AUDIT_ORDER -> {
                    tvOrderDeal.visible()
                    tvOrderConfirm.visible()
                    tvOrderDeal.text = "驳回"
                    tvOrderConfirm.text = "通过"
                    tvOrderDeal.background =
                        tvOrderDeal.context.getDrawable(R.drawable.bg_edittext_corner)
                    tvOrderConfirm.background =
                        tvOrderConfirm.context.getDrawable(R.drawable.bg_blue_storke_btn)
                }
                OrderStatus.HAVE_DELIVERY_ORDER -> {
                    tvOrderDeal.visible()
                    tvOrderConfirm.visible()
                    tvOrderDeal.text = "查看物流"
                    tvOrderConfirm.text = "完成"
                    tvOrderDeal.background =
                        tvOrderDeal.context.getDrawable(R.drawable.bg_edittext_corner)
                    tvOrderConfirm.background =
                        tvOrderConfirm.context.getDrawable(R.drawable.bg_blue_storke_btn)
                }
                OrderStatus.UN_DELIVERY_AND_PAY,
                OrderStatus.UN_DELIVERY_ORDER -> {
                    tvOrderDeal.gone()
                    tvOrderConfirm.visible()
                    tvOrderConfirm.text = "去发货"
                    tvOrderConfirm.background =
                        tvOrderConfirm.context.getDrawable(R.drawable.bg_blue_storke_btn)
                }
                OrderStatus.UN_PAY, OrderStatus.PAY_FAIL -> {
                    tvOrderDeal.gone()
                    tvOrderConfirm.visible()
                    tvOrderConfirm.text = "收款"
                    tvOrderConfirm.background =
                        tvOrderConfirm.context.getDrawable(R.drawable.bg_blue_storke_btn)
                }

                OrderStatus.UN_TAKE_DRUG -> {
                    tvOrderDeal.gone()
                    tvOrderConfirm.visible()
                    tvOrderConfirm.text = "确认取药"
                    tvOrderConfirm.background =
                        tvOrderConfirm.context.getDrawable(R.drawable.bg_blue_storke_btn)
                }
                else -> {
                    tvOrderDeal.gone()
                    tvOrderConfirm.gone()
                }
            }
        }
    }

    private class OrderDiffCallback : DiffUtil.ItemCallback<BaseItem>() {
        override fun areContentsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
            return oldItem.bId == newItem.bId
        }

        override fun areItemsTheSame(oldItem: BaseItem, newItem: BaseItem): Boolean {
            return oldItem == newItem
        }
    }
}