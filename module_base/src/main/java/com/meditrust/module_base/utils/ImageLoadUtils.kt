package com.meditrust.module_base.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.meditrust.module_base.R

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/29
 * @desc:
 */
object ImageLoadUtils {

    fun loadUrl(url: String, imageView: ImageView) {
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

    /**
     * 加载圆形图片
     * @param url
     * @param imageView
     */
    fun loadCircle(url: String, imageView: ImageView) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.icon_avotor)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(MultiTransformation<Bitmap>(CenterCrop(), CircleCrop()))


        Glide.with(imageView.context)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }

    /**
     * 加载福利图片
     * @param url
     * @param imageView
     */
    fun loadWelfareUrl(
        mContext: Context,
        url: String,
        imageView: ImageView,
        width: Int,
        height: Int
    ) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.icon_welfare_default)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .override(width, height)
            .centerCrop()


        Glide.with(mContext)
            .load(url)
            .apply(requestOptions)
            .into(imageView)
    }
}