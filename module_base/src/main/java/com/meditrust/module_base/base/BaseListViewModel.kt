package com.meditrust.module_base.base

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.constant.RefreshResult
import com.meditrust.module_base.datasource.DataSourceFactory
import com.meditrust.module_base.datasource.PagedDataLoader

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc:
 */
abstract class BaseListViewModel<T>(application: Application) : BaseViewModel(application), PagedDataLoader<T> {

    private val data = ArrayList<T>()

    open fun pageSize(): Int {
        return Const.PAGE_SIZE
    }

    private val dataSourceFactory: DataSourceFactory<T> by lazy {
        DataSourceFactory(this)
    }

    private val loadLiveData = LivePagedListBuilder(dataSourceFactory, pageSize()).build()

    private val refreshLiveData: MutableLiveData<RefreshResult> = MutableLiveData()

    private val loadMoreLiveData: MutableLiveData<RefreshResult> by lazy { MutableLiveData<RefreshResult>() }

    private val notifyItemLiveData: MutableLiveData<Pair<Int, Any?>> by lazy { MutableLiveData<Pair<Int, Any?>>() }

    private val removeItemLiveData: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    fun invalidate() {
        dataSourceFactory.sourceLiveData.value?.invalidate()
    }

    override fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, T>
    ) {
        refresh()
        data.clear()
        loadData(1) {
            when {
                it == null -> refreshLiveData.value = RefreshResult.FAILED
                it.isEmpty() -> refreshLiveData.value = RefreshResult.NO_DATA
                it.size < Const.PAGE_SIZE -> {
                    data.addAll(it)
                    callback.onResult(it, null, null)
                    refreshLiveData.value = RefreshResult.NO_MORE
                }
                else -> {
                    data.addAll(it)
                    callback.onResult(it, null, 2)
                    refreshLiveData.value = RefreshResult.SUCCEED
                }
            }
        }
    }

    override fun loadAfter(
        params: PageKeyedDataSource.LoadParams<Int>,
        callback: PageKeyedDataSource.LoadCallback<Int, T>
    ) {
        loadMore()
        loadData(params.key) {
            when {
                it == null -> loadMoreLiveData.value = RefreshResult.FAILED
                it.size < pageSize() -> {
                    data.addAll(it)
                    callback.onResult(it, null)
                    loadMoreLiveData.value = RefreshResult.NO_MORE
                }
                else -> {
                    data.addAll(it)
                    callback.onResult(it, params.key + 1)
                    loadMoreLiveData.value = RefreshResult.SUCCEED
                }
            }
        }
    }

    fun observeDataObserver(
        owner: LifecycleOwner,
        data: (PagedList<T>) -> Unit,
        refreshResult: (RefreshResult) -> Unit,
        loadMoreResult: (RefreshResult) -> Unit
    ) {
        loadLiveData.observe(owner, Observer {
            it.apply(data)
        })
        refreshLiveData.observe(owner, Observer {
            refreshResult(it)
        })
        loadMoreLiveData.observe(owner, Observer {
            loadMoreResult(it)
        })
    }

    fun observeAdapterObserver(
        owner: LifecycleOwner,
        notifyItem: (Int, Any?) -> Unit,
        removeItem: (Int) -> Unit
    ) {
        notifyItemLiveData.observe(owner, Observer {
            it?.apply { notifyItem(first, second) }
        })

        removeItemLiveData.observe(owner, Observer {
            removeItem(it!!)
        })
    }

    fun notifyItem(position: Int) {
        notifyItemLiveData.value = Pair(position, null)
    }

    fun notifyItem(position: Int, payload: Any?) {
        notifyItemLiveData.value = Pair(position, payload)
    }

    fun removeItem(position: Int) {
        removeItemLiveData.value = position
    }

    override fun refresh() {
    }

    override fun loadMore() {
    }

    abstract fun loadData(page: Int, onResponse: (ArrayList<T>?) -> Unit)
}