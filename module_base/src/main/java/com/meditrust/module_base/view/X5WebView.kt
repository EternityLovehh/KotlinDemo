package com.meditrust.module_base.view

import android.content.Context
import android.util.AttributeSet
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/8
 * @desc:
 */
class X5WebView : WebView {
    constructor(context: Context?) : super(context) {
        initWebViewSetting()
        setBackgroundColor(85621)
    }

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context, attributeSet) {
        this.webViewClient = client
        initWebViewSetting()
    }

    private fun initWebViewSetting() {
        val mWebviewSettings: WebSettings = this.getSettings()
        mWebviewSettings.setJavaScriptEnabled(true)
        mWebviewSettings.setJavaScriptCanOpenWindowsAutomatically(true)
        mWebviewSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
        mWebviewSettings.setSupportZoom(true)
        //设置控件可缩放
        mWebviewSettings.setBuiltInZoomControls(true)
        //设置屏幕自适应
        mWebviewSettings.setUseWideViewPort(true)
        mWebviewSettings.setSupportMultipleWindows(true)
        mWebviewSettings.setAppCacheEnabled(true)
        //使用localStorage
        mWebviewSettings.setDomStorageEnabled(true)
        mWebviewSettings.setGeolocationEnabled(true)
        //保存表单数据
        mWebviewSettings.setSaveFormData(true)
        //允许访问文件
        mWebviewSettings.setAllowFileAccess(true)
        mWebviewSettings.setAppCacheMaxSize(Long.MAX_VALUE)
        mWebviewSettings.setPluginState(WebSettings.PluginState.ON_DEMAND)
        mWebviewSettings.setCacheMode(WebSettings.LOAD_NO_CACHE)
    }

    val client = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(webView: WebView?, p1: String?): Boolean {
            webView?.loadUrl(p1)
            return true
        }
    }


}