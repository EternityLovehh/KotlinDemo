package com.meditrust.module_drug.model

import android.text.TextUtils
import com.meditrust.module_base.model.BaseItem

import java.io.Serializable

/**
 * @author: create by zhongchao.wang
 * @date: 2019/8/8
 * @desc: 药品列表
 */
class DrugModel(
    /**
     * commodityDesc :
     * commodityDetailImageUrl :
     * commodityId : 5542
     * commodityImageUrl :
     * commodityName : 傲朴舒
     * commodityPrice : 30000
     * commodityRemark :
     * commodityStockQty : 999999
     * drugCategory : 处方药
     * drugCommonName : 马昔腾坦片
     * drugEnglishName : opsumit
     * drugId : 5617
     * drugImageUrl :
     * drugLicenseNumber :
     * drugManufacturer : actelion
     * drugName : 傲朴舒
     * drugSku : opsumit
     * drugSmallImageUrl :
     * drugSpec : 10mg*30
     * drugUnit : 盒
     * imageUrls : []
     * remark1 :
     * remark10 :
     * remark11 :
     * remark12 :
     * remark13 :
     * remark14 :
     * remark15 :
     * remark2 :
     * remark3 :
     * remark4 :
     * remark5 :
     * remark6 :
     * remark7 :
     * remark8 :
     * remark9 :
     * searchKeywords : 傲朴舒 opsumit 马昔腾坦片 肺动脉高压 opsumit
     */
    var commodityDesc: String,
    var commodityDetailImageUrl: String,
    var commodityId: Int = 0,
    var commodityImageUrl: String,
    var commodityName: String,
    var commodityPrice: Double = 0.toDouble(),
    var commodityRemark: String,
    var commodityStockQty: Int = 0,
    var drugCategory: String,
    var drugCommonName: String,
    var drugEnglishName: String,
    var drugId: Int = 0,
    var drugImageUrl: String,
    var drugLicenseNumber: String,
    var drugManufacturer: String,
    var drugName: String = "hhaha",
    var drugSku: String,
    var drugSmallImageUrl: String,
    var drugSpec: String,
    var drugUnit: String,
    var remark1: String,
    var remark10: String,
    var remark11: String,
    var remark12: String,
    var remark13: String,
    var remark14: String,
    var remark15: String,
    var remark2: String,
    var remark3: String,
    var remark4: String,
    var remark5: String,
    var remark6: String,
    var remark7: String,
    var remark8: String,
    var remark9: String,
    var searchKeywords: String,
    var imageUrls: List<*>,
    var type: String,
    var isChecked: Boolean? = false
) : Serializable, BaseItem() {
    //临时字段
    var tempResp: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                if (commodityStockQty == 0) {
                    this.tempResp = "0"
                } else {
                    this.tempResp = commodityStockQty.toString()
                }
            }
            return field
        }
    var tempPrice: String? = null
        get() {
            if (TextUtils.isEmpty(field)) {
                this.tempPrice = commodityPrice.toString()
            }
            return field
        }
    var drugNum = 1

    val totalPrice: Double
        get() = commodityPrice * drugNum

    fun isChecked(): Boolean {
        return isChecked!!
    }

    fun setChecked(checked: Boolean) {
        isChecked = checked
    }
}
