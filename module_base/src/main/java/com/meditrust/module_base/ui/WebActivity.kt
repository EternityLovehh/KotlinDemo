package com.meditrust.module_base.ui

import android.graphics.PixelFormat
import android.os.Build
import android.view.View
import android.widget.ProgressBar
import com.meditrust.module_base.BR
import com.meditrust.module_base.R
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_base.base.BaseViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.databinding.ActivityWebBinding
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.layout_toolbar.*

class WebActivity : BaseActivity<ActivityWebBinding, BaseViewModel>() {
    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_web
    }

    override fun initData() {
        val title = intent.getStringExtra(Const.WEB_TITLE)
        val url = intent.getStringExtra(Const.WEB_URL)
        setSupportActionBar(toolbar)
        tv_title.text = title
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.navigationIcon = getDrawable(R.drawable.icon_black_back)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initHardwareAccelerate()
        window.setFormat(PixelFormat.TRANSLUCENT)

        mBinding.webView.webChromeClient = client
        mBinding.webView.loadUrl(url)

    }

    val client = object : WebChromeClient() {
        override fun onProgressChanged(p0: WebView?, p1: Int) {
            val progressBar = ProgressBar(applicationContext)
            if (p1 < 100) {
                progressBar.progress = p1
                progressBar.max = 100
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
            super.onProgressChanged(p0, p1)
        }
    }


    /**
     * 启用硬件加速
     */
    private fun initHardwareAccelerate() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                window.setFlags(
                    android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun initViewModel(): BaseViewModel {
        return BaseViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.webView.apply {
            this.destroy()
            this.stopLoading()
            this.clearHistory()
            this.removeAllViews()
        }
    }

}
