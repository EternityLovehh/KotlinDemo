package com.meditrust.module_base.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.meditrust.module_base.constant.RefreshResult
import java.util.concurrent.Executor

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc:
 */
abstract class PagedDataSource<Key, T>(private val retryExecutor: Executor) : PageKeyedDataSource<Key, T>() {
    val networkState by lazy {
        MutableLiveData<RefreshResult>()
    }
    val initialLoad by lazy {
        MutableLiveData<RefreshResult>()
    }

    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute { it.invoke() }
        }
    }

    override fun loadInitial(params: LoadInitialParams<Key>, callback: LoadInitialCallback<Key, T>) {
        try {
            val response: T = getInitResponse()
            callback.onResult(response, null, response.after())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun loadAfter(params: LoadParams<Key>, callback: LoadCallback<Key, T>) {

    }

    override fun loadBefore(params: LoadParams<Key>, callback: LoadCallback<Key, T>) {
    }


    // 首次请求返回的结果
    abstract fun <T> getInitResponse(): T

    // loadmore返回的结果
    abstract fun <T> getAfterResponse(key: Key): T

}