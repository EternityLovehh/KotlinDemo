package com.meditrust.module_drug.welfare

import android.app.Application
import com.meditrust.module_base.base.BaseListViewModel
import com.meditrust.module_base.constant.Const
import com.meditrust.module_base.extensions.io_main
import com.meditrust.module_base.extensions.subscribeBy
import com.meditrust.module_base.utils.ToastUtils
import com.meditrust.module_drug.api.ApiService
import com.meditrust.module_drug.model.RecruitModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject

/**
 * @author: create by zhongchao.wang
 * @date: 2019/9/18
 * @desc:
 */
class WelfareViewModel(application: Application) : BaseListViewModel<RecruitModel>(application) {

    override fun pageSize(): Int {
        return Int.MAX_VALUE
    }

    override fun loadData(page: Int, onResponse: (ArrayList<RecruitModel>?) -> Unit) {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("type", null)
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        val requestBody = jsonObject.toString().toRequestBody(Const.JSON.toMediaTypeOrNull())
        ApiService.queryRecruit(requestBody)
            .io_main()
            .subscribeBy({
                onResponse(it as ArrayList<RecruitModel>?)
            }, {
                ToastUtils.showToast(it)
            })
            .add()
    }

}