package com.meditrust.module_drug.extensions

import android.app.Activity
import androidx.fragment.app.Fragment
import com.meditrust.module_base.MyApplication
import com.meditrust.module_drug.component.ActivityComponent
import com.meditrust.module_drug.component.DaggerActivityComponent
import com.meditrust.module_drug.component.DaggerFragmentComponent
import com.meditrust.module_drug.component.FragmentComponent

/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/15
 * @desc:
 */

fun Activity.component(): ActivityComponent =
    DaggerActivityComponent.builder()
        .appComponent(MyApplication.instance.appComponent)
        .build()

fun Fragment.component(): FragmentComponent =
    DaggerFragmentComponent.builder()
        .appComponent(MyApplication.instance.appComponent)
        .build()