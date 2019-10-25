package com.meditrust.module_drug.drug

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meditrust.module_base.adapter.BaseBindingAdapter
import com.meditrust.module_base.base.BaseListActivity
import com.meditrust.module_base.extensions.init
import com.meditrust.module_base.listener.OnTabClickListener
import com.meditrust.module_base.popup.CommonPopupWindow
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.AllDrugAdapter
import com.meditrust.module_drug.databinding.ActivityAllDrugBinding
import com.meditrust.module_drug.extensions.component
import com.meditrust.module_drug.model.DrugModel
import com.meditrust.module_drug.model.FilterModel
import kotlinx.android.synthetic.main.activity_all_drug.*
import kotlinx.android.synthetic.main.layout_drawer_content.*
import javax.inject.Inject

class AllDrugActivity : BaseListActivity<DrugModel, ActivityAllDrugBinding, AllDrugViewModel>() {

    private val upDrug = "N"
    private var minPrice: String? = null
    private var maxPrice: String? = null
    private var queryText: String? = null
    private var mPrices: Array<String> = emptyArray()
    private var mRemark: String? = null
    private var mPriceList: List<String> = listOf()
    private var popupWindow: CommonPopupWindow? = null
    var lastPos: Int = -1

    @Inject
    lateinit var adapter: AllDrugAdapter

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_all_drug
    }

    override fun initData() {

        setToolBar(mBinding.include.toolbar, mBinding.include.tvTitle, "已上架药品")

        mBinding.rlAllDrug.init(adapter)

        mViewModel.observeDataObserver(this,
            { adapter.submitList(it) },
            { refreshFinished(it, mBinding.srlAllDrug, mBinding.emptyView) },
            { loadMoreFinished(it) })

        mBinding.srlAllDrug.setOnRefreshListener {
            mViewModel.invalidate()
        }

        fv_menu.setTabClickListener(object : OnTabClickListener {
            override fun onTabClick(position: Int) {
                when (position) {
                    0 -> {
                        drawer_layout.openDrawer(drawer_content)
                    }
                    1 -> {
                        showPopup()
                    }
                }
            }
        })
    }

    override fun initViewModel(): AllDrugViewModel {
        return AllDrugViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initJect() {
        component().inject(this)
    }


    fun setRequestBody() {
        mViewModel.apply {
            deleted = upDrug
            drugTypes = mPrices
            maxPrice = maxPrice
            minPrice = minPrice
            remark = mRemark
            keywords = queryText
        }
    }

    fun showPopup() {
        popupWindow = CommonPopupWindow.Builder(this)
            .setView(R.layout.layout_filter_list)
            .setViewOnclickListener(listener)
            .setWidthAndHeight(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            .setAnimationStyle(R.style.CommonPopupWindow)
            .create()
        popupWindow?.showPopupWindow(fv_menu)
    }

    private val listener = object : CommonPopupWindow.ViewInterface {
        override fun getChildView(view: View, layoutResId: Int) {
            when (layoutResId) {
                R.layout.layout_filter_list -> {
                    val recyclerFilter: RecyclerView = view.findViewById(R.id.recycler_filter)
                    val rlMask: View = view.findViewById(R.id.rl_mask)
                    popupWindow?.setMaskView(rlMask)
                    var adapter =
                        BaseBindingAdapter<FilterModel>(
                            BR.bean,
                            R.layout.item_filter
                        )
                    recyclerFilter.init(adapter)
                    mPriceList = resources.getStringArray(R.array.price_list).toList()
                    val mFilterList: MutableList<FilterModel> = mutableListOf()
                    for (value in mPriceList) {
                        val filterModel = FilterModel()
                        filterModel.setName(value)
                        filterModel.setSelected(false)
                        mFilterList.add(filterModel)
                    }
                    adapter.setData(mFilterList)
                    adapter.setOnItemClickListener(object : BaseBindingAdapter.OnItemClickListener {
                        override fun itemClick(position: Int) {

                            priceRange(position)

                            if (lastPos != position) {
                                mFilterList[position].setSelected(true)
                            } else {
                                mFilterList[lastPos].setSelected(true)
                            }
                            lastPos = position
                            mFilterList?.let {
                                it.forEach { it1 ->
                                    if (it1.getName() != mFilterList[lastPos].getName()) {
                                        it1.setSelected(false)
                                    }
                                }
                            }
                            setRequestBody()
                            mViewModel.invalidate()
                            popupWindow?.dismiss()
                        }
                    })

                    rlMask?.setOnClickListener {
                        popupWindow?.dismiss()
                    }
                }

            }
        }
    }

    /**
     * 价格区间
     */
    private fun priceRange(position: Int) {
        when (position) {
            0 -> {
                maxPrice = null
                minPrice = maxPrice
            }
            mPriceList.size - 1 -> {
                minPrice = "2000"
                maxPrice = null
            }
            else -> {
                val splitPrice = mPriceList?.get(position)?.split("-|\\(".toRegex())
                    .dropLastWhile { it.isEmpty() }.toTypedArray()
                minPrice = splitPrice[0].trim()
                maxPrice = splitPrice[1].trim()
            }
        }
    }

}
