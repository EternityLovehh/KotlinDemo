package com.meditrust.moudle_user.ui

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_base.model.GuideModel
import com.meditrust.moudle_user.BR
import com.meditrust.moudle_user.R
import com.meditrust.moudle_user.adapter.GuidePageAdapter
import com.meditrust.moudle_user.databinding.ActivityGuideBinding
import com.meditrust.moudle_user.viewmodel.GuideViewModel

class GuideActivity : BaseActivity<ActivityGuideBinding, GuideViewModel>() {

    private var mGuideList: ArrayList<GuideModel> = ArrayList()
    private lateinit var mAdapter: GuidePageAdapter

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_guide
    }

    override fun initData() {
        val textArray = resources.getStringArray(R.array.guide_descs)
        val imgArray =
            arrayListOf<Int>(R.drawable.icon_guide_first, R.drawable.icon_guide_second, R.drawable.icon_guide_three)
        for (index in 0 until textArray.size) {
            var guideModel = GuideModel(imgArray[index], textArray[index])
            mGuideList.add(guideModel)
        }
        mAdapter = GuidePageAdapter(this, mGuideList)

        mBinding.vpGuide.adapter = mAdapter
        mBinding.uiIndicator.attachToViewPager2(mBinding.vpGuide)
        mBinding.vpGuide.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == mAdapter.itemCount - 1) {
                    mBinding.tvGuide.visibility = View.VISIBLE
                    mBinding.uiIndicator.visibility = View.GONE
                } else {
                    mBinding.tvGuide.visibility = View.GONE
                    mBinding.uiIndicator.visibility = View.VISIBLE
                }
                mBinding.vpGuide.adapter?.let {
                    mBinding.uiIndicator.setSelection(mBinding.vpGuide.currentItem % mAdapter.itemCount)
                }
                mBinding.uiIndicator.postInvalidate()
            }

        })
    }

    override fun initViewModel(): GuideViewModel {
        return GuideViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

}
