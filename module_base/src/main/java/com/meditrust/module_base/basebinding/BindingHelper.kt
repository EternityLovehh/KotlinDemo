package com.meditrust.module_base.basebinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/19
 * @desc:
 */
object BindingHelper {

    @BindingAdapter("imgSrc")
    @JvmStatic
    fun setGuideImg(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }
}