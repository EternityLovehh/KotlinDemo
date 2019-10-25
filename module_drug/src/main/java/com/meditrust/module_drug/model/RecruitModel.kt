package com.meditrust.module_drug.model

import com.meditrust.module_base.model.BaseItem
import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/25
 * @desc:
 */
data class RecruitModel(
    /**
     * categoryLabel : string
     * clickTimes : 0
     * id : 0
     * introduction : string
     * isDeleted : string
     * pharmacyId : 0
     * projectId : string
     * thumbnail : string
     * title : string
     * type : string
     */

    var categoryLabel: String? = null,
    var clickTimes: Int = 0,
    var id: Int = 0,
    var introduction: String? = null,
    var isDeleted: String? = null,
    var pharmacyId: Int = 0,
    var projectId: String? = null,
    var thumbnail: String? = null,
    var title: String? = null,
    var type: String? = null,
    var points: Int = 0
) : Serializable, BaseItem()
