package com.meditrust.module_base.model

/**
 * @author Yang Shihao
 * @date 2018/11/21
 */
data class ListPaged<T>(
    var count: Int = 0,
    var current: Int = 0,
    var pageSize: Int = 0,
    var totalPage: Int = 0,
    var results: ArrayList<T>
)