package com.meditrust.module_base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/22
 * @desc:
 */
class CommonPageAdapter(
    fm: FragmentManager,
    private val fragmentList: List<Fragment>,
    private val titleList: List<String>?
) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): String? {
        return titleList?.get(position)
    }
}