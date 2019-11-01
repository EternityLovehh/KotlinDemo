package com.meditrust.module_drug.workroom.detail

import android.annotation.TargetApi
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import com.meditrust.module_base.base.BaseActivity
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.utils.DpUtils
import com.meditrust.module_base.utils.ImageLoadUtils
import com.meditrust.module_drug.BR
import com.meditrust.module_drug.R
import com.meditrust.module_drug.databinding.ActivityRecruitDetailBinding
import com.meditrust.module_drug.upload.UploadIdCardActivity
import com.tencent.smtt.export.external.interfaces.WebResourceError
import com.tencent.smtt.export.external.interfaces.WebResourceRequest
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import kotlinx.android.synthetic.main.activity_recruit_detail.*
import org.jetbrains.anko.startActivity

class RecruitDetailActivity : BaseActivity<ActivityRecruitDetailBinding, RecruitDetailViewModel>() {

    var mRecruitId: Int = 0
    lateinit var mRecruitType: String
    lateinit var mRecruitTitle: String

    override fun getStausBar(): Boolean? {
        return true
    }

    override fun initView(): Int {
        return R.layout.activity_recruit_detail
    }

    override fun initData() {
        mRecruitTitle = intent.getStringExtra(Const.RECRUIT_TITLE)
        mRecruitId = intent.getIntExtra(Const.RECRUIT_ID, 0)
        mRecruitType = intent.getStringExtra(Const.RECRUIT_TYPE)

        setToolBar(mBinding.include.toolbar, mBinding.include.tvTitle, mRecruitTitle)

        mViewModel.recruitType(mRecruitType)

        mViewModel.queryRecruitDetail(mRecruitId.toString())
        mViewModel.addClick(mRecruitId.toString())

        showDetail()

        addListener()

        //硬件加速
        initHardwareAccelerate()

        showWeb()

    }

    private fun initHardwareAccelerate() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                window
                    .setFlags(
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                        WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                    )
                web_recruit_detail.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun showWeb() {
        web_recruit_detail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view!!.loadUrl(url)
                return true
            }

            override fun onPageFinished(webView: WebView?, s: String?) {
                super.onPageFinished(webView, s)
                if (!webView!!.settings.loadsImagesAutomatically) {
                    webView.settings.loadsImagesAutomatically = true
                }
                ll_bottom_content.setVisibility(View.VISIBLE)
            }

            override fun onReceivedError(
                webView: WebView?,
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                super.onReceivedError(webView, errorCode, description, failingUrl)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    return
                }
                webView!!.visibility = View.GONE
            }

            // 新版本,只会在Android6.0及以上调用
            @TargetApi(Build.VERSION_CODES.M)
            override fun onReceivedError(
                webView: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(webView, request, error)
                if (request.isForMainFrame) {
                    // 在这里显示自定义错误页
                    webView.visibility = View.GONE
                }

            }
        }
    }

    private fun addListener() {
        mBinding.ivWriteInfo.setOnClickListener {
            when (mRecruitType) {
                Const.DRUG_RECRUIT -> {
//                    startActivity<AddUserActivity>()
                }
                Const.STAMP_RECRYUT -> {
                    startActivity<UploadIdCardActivity>(Pair(Const.RECRUIT_ID, mRecruitId))
                }
            }
        }
    }

    private fun showDetail() {
        mViewModel.mRecruitDetailLiveData?.observe(this, Observer {
            web_recruit_detail.loadDataWithBaseURL(null, it.content, "text/html", "utf-8", null)
            if (TextUtils.equals(mRecruitId.toString(), 344.toString())) {
                iv_recruit_qrcode.setImageResource(R.mipmap.icon_medicine)
            } else {
                ImageLoadUtils.loadWelfareUrl(
                    this,
                    it?.qrUrl!!,
                    iv_recruit_qrcode,
                    DpUtils.dp2px(160f),
                    DpUtils.dp2px(160f)
                )
            }
        })
    }

    override fun initViewModel(): RecruitDetailViewModel {
        return RecruitDetailViewModel(application)
    }

    override fun initVariableId(): Int {
        return BR.viewmodel
    }
}
