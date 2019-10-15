package com.meditrust.module_drug.fragment

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.meditrust.module_base.BR
import com.meditrust.module_base.base.BaseFragment
import com.meditrust.module_drug.R
import com.meditrust.module_drug.adapter.MainPagerAdapter
import com.meditrust.module_drug.databinding.FragmentWorkroomBinding
import com.meditrust.module_drug.viewmodel.WorkRoomViewModel

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class WorkRoomFragment : BaseFragment<FragmentWorkroomBinding, WorkRoomViewModel>() {
    private lateinit var tabItems: Array<String>
    private lateinit var mFragmentList: List<Fragment>
    private lateinit var mPageAdapter: MainPagerAdapter

    override fun isLazy(): Boolean {
        return true
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun initView(): Int {
        return R.layout.fragment_workroom
    }

    override fun initData() {
        tabItems = resources.getStringArray(R.array.tab_items)
        mFragmentList = listOf(MyPharmacyFragment(), MyPharmacyFragment(), MyPharmacyFragment())

        mPageAdapter = MainPagerAdapter(childFragmentManager, lifecycle, mFragmentList)
        mBinding.vpWorkRoom.adapter = mPageAdapter

        TabLayoutMediator(mBinding.tlWork, mBinding.vpWorkRoom) { tab, position ->
            setCustomTab(tab, position)
        }.attach()
    }

    private fun setCustomTab(tab: TabLayout.Tab, position: Int) {
        var view = layoutInflater.inflate(R.layout.item_tab_msg, null)
        var tabTitle: TextView = view.findViewById<View>(R.id.tv_tab_title) as TextView
        var ivMsgDot: ImageView = view.findViewById<View>(R.id.iv_msg_dot) as ImageView
        when (position) {
            0 -> {
                tabTitle.text = tabItems[position]
                ivMsgDot.visibility = View.GONE
                tab.customView = view
            }
            1 -> {
                var view = layoutInflater.inflate(R.layout.item_icon_tab, null)
                tab.customView = view
            }
            2 -> {
                tabTitle.text = tabItems[position]
                ivMsgDot.visibility = View.VISIBLE
                tab.customView = view
            }
        }

    }

    override fun initViewModel(): WorkRoomViewModel {
        return WorkRoomViewModel(activity!!.application)
    }

}