package com.meditrust.kotlindemo

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.meditrust.kotlindemo.databinding.ActivityMainBinding
import com.meditrust.module_base.adapter.CommonPageAdapter
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_drug.welfare.WelfareFragment
import com.meditrust.module_drug.workroom.WorkRoomFragment
import com.meditrust.moudle_user.fragment.PersonFragment
import com.meditrust.moudle_user.fragment.WithDrawalFragment


@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private lateinit var mFragmentList: List<Fragment>
    private lateinit var mAdapter: CommonPageAdapter
    private var mToolbar: Toolbar? = null
    private var mTitle: TextView? = null

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        mToolbar = findViewById(R.id.toolbar)
        mTitle = findViewById(R.id.tv_title)
        setSupportActionBar(mToolbar)
        mTitle?.text = "首页"
        mBinding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        mFragmentList = listOf(WorkRoomFragment(), WelfareFragment(), WithDrawalFragment(), PersonFragment())
        mAdapter = CommonPageAdapter(this.supportFragmentManager, mFragmentList, null)
        mBinding.vpMain?.apply {
            this.adapter = mAdapter
        }
    }

    override fun initViewModel(): MainViewModel {
        return MainViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                mBinding.vpMain.currentItem = 0
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_welfare -> {
                mBinding.vpMain.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_withdrawal -> {
                mBinding.vpMain.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_persion -> {
                mBinding.vpMain.currentItem = 3
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

}
