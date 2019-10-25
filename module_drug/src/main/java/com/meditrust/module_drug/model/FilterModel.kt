package com.meditrust.module_drug.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.meditrust.module_drug.BR

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/18
 * @desc:
 */
class FilterModel(
    private var name: String? = null,
    private var selected: Boolean = false
) : BaseObservable() {

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    @Bindable
    fun getSelected(): Boolean {
        return selected
    }

    fun setSelected(selected: Boolean) {
        this.selected = selected
        notifyPropertyChanged(BR.selected)
    }

}