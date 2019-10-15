package com.meditrust.module_base.base

import android.app.Application
import androidx.lifecycle.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/7
 * @desc:
 */
open class BaseViewModel : AndroidViewModel, LifecycleObserver {

    /**
     * 网络是否可用
     */
    protected var isNetworkAvailable = MutableLiveData<Boolean>()
    /**
     * 页面无数据显示
     */
    protected var isNoData = MutableLiveData<Boolean>()

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    open fun Disposable.add() {
        compositeDisposable.add(this)
    }

    constructor(application: Application) : super(application)

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestory() {
        onCleared()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        isNoData.value = false
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun getIsNetworkAvailable(): MutableLiveData<Boolean> {
        return isNetworkAvailable
    }

    fun setIsNetworkAvailable(isNetworkAvailable: Boolean?) {
        this.isNetworkAvailable.value = isNetworkAvailable
    }

    fun getIsNoData(): MutableLiveData<Boolean> {
        return isNoData
    }

    fun setIsNoData(isNoData: Boolean?) {
        this.isNoData.value = isNoData
    }

}