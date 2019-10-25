package com.meditrust.module_drug.drug

import android.app.Application
import com.meditrust.module_base.base.BaseListViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.model.DrugModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/15
 * @desc:
 */
class AllDrugViewModel(application: Application) : BaseListViewModel<DrugModel>(application) {

    var deleted: String = "N"
    var drugTypes: Array<String>? = null
    var maxPrice: String? = null
    var minPrice: String? = null
    var remark: String? = null
    var keywords: String? = null

    override fun loadData(page: Int, onResponse: (ArrayList<DrugModel>?) -> Unit) {
        val jsonObject = JSONObject()
        try {
            val jsonArray = JSONArray()
            jsonObject.put("deleted", deleted)
            drugTypes?.forEach {
                jsonArray.put(it)
            }
            jsonObject.put("drugCategories", jsonArray)
            jsonObject.put("maxPrice", maxPrice)
            jsonObject.put("minPrice", minPrice)
            jsonObject.put("page", page)
            jsonObject.put("pageSize", Const.PAGE_SIZE)
            jsonObject.put("remark1", remark)
            jsonObject.put("searchKeywords", keywords)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.queryDrugList(requestBody)
            .io_main()
            .subscribeBy(
                {
                    onResponse(it?.results)
                },
                {
                    onResponse(null)
                }
            )
            .add()
    }

}