package com.meditrust.module_base.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource


/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/11
 * @desc:
 */
abstract class DataSourceFactory<Key, Value> : DataSource.Factory<Key, Value>() {

    val sourceLiveData = MutableLiveData<PagedDataSource>()

    override fun create(): DataSource<Key, Value>? {
        val dataSource = getSource()
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    protected abstract fun getSource(): PagedDataSource

    abstract fun setParams(pageSize: Int, vararg params: Any)
}