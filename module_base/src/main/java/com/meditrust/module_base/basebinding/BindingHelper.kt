package com.meditrust.module_base.basebinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.meditrust.module_base.R
import com.meditrust.module_base.utils.DpUtils


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

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.icon_order_default)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(DpUtils.dp2px(64f), DpUtils.dp2px(64f))
            .centerCrop()
        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }


}