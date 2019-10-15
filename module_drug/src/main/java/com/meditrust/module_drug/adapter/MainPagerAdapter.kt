package com.meditrust.module_drug.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/17
 * @desc:
 */
class MainPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val mFragmentManager: FragmentManager
    private var mLifecycle: Lifecycle
    private lateinit var mFragmentList: List<Fragment>

    init {
        this.mFragmentManager = fragmentManager
        this.mLifecycle = lifecycle
    }

    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragmentList: List<Fragment>) : this(
        fragmentManager,
        lifecycle
    ) {
        this.mFragmentList = fragmentList
    }

    override fun getItemCount(): Int {
        return this.mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }


}