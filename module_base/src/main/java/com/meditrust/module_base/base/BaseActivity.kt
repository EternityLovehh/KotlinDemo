package com.meditrust.module_base.base

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.meditrust.module_base.R
import com.meditrust.module_base.listener.OnClickListener
import com.meditrust.module_base.manager.ActivityManager
import com.meditrust.module_base.utils.KeyBoardUtils
import com.meditrust.module_base.utils.ToastUtils

abstract class BaseActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity {

    private var menuIcon: Int? = null
    private var menuName: String? = null
    private var onRightClickListener: OnClickListener? = null

    protected lateinit var mBinding: V
    protected lateinit var mViewModel: VM

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, initView())
        mViewModel = initViewModel()
        mBinding.setVariable(initVariableId(), mViewModel)
        mBinding.executePendingBindings()
        mBinding.lifecycleOwner = this

        setLightStatusBar(getStausBar())

        initJect()
        initData()
        ActivityManager.instance?.addActivity(this)
    }

    fun setToolBar(toolbar: Toolbar, tvTitle: TextView, title: String) {
        setSupportActionBar(toolbar)
        tvTitle.text = title
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.navigationIcon = getDrawable(R.drawable.icon_black_back)
    }

    private fun setLightStatusBar(statusBar: Boolean?) {
        // 如果亮色，设置状态栏文字为黑色
        when (statusBar) {
            true -> {
                // 设置状态栏底色颜色
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.statusBarColor = resources.getColor(android.R.color.transparent)
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.statusBarColor = resources.getColor(android.R.color.darker_gray)
                }
                //实现状态栏图标和文字颜色为暗色
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            false -> {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuName.let {
            return false
        }
        menuInflater.inflate(R.menu.menu_top_bar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuIcon?.let {
            menu?.findItem(R.id.menu)?.icon = resources.getDrawable(R.drawable.icon_white_back)
        }

        menuName?.let {
            menu?.findItem(R.id.menu)?.title = menuName
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu -> onRightClickListener?.onClick()
        }
        return super.onOptionsItemSelected(item)
    }

    fun setRightButton(name: String, icon: Int, rightListener: OnClickListener) {
        this.menuName = name
        this.menuIcon = icon
        this.onRightClickListener = rightListener
    }

    /**
     * 简化toast
     *
     * @param text
     */
    fun showToast(text: String) {
        ToastUtils.showToast(text)
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel!!.onDestory()
        ActivityManager.instance?.removeActivity(this)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        currentFocus?.let {
            when (ev?.action) {
                MotionEvent.ACTION_DOWN -> KeyBoardUtils.hideKeyBoard(ev, it, this)
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    abstract fun getStausBar(): Boolean?

    abstract fun initView(): Int

    abstract fun initData()

    open fun initJect() {}

    abstract fun initViewModel(): VM

    abstract fun initVariableId(): Int
}
