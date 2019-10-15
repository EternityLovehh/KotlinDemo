package com.meditrust.module_base.datasource

import androidx.paging.PageKeyedDataSource

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/11
 * @desc:
 */
interface PagedDataLoader<T> {

    fun loadInitial(
        params: PageKeyedDataSource.LoadInitialParams<Int>,
        callback: PageKeyedDataSource.LoadInitialCallback<Int, T>
    )

    fun loadAfter(params: PageKeyedDataSource.LoadParams<Int>, callback: PageKeyedDataSource.LoadCallback<Int, T>)

    fun refresh()

    fun loadMore()

}