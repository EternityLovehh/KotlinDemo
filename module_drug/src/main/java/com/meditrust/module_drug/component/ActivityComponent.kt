package com.meditrust.module_drug.component

import com.meditrust.module_base.component.AppComponent
import com.meditrust.module_base.scope.ActivityScope
import com.meditrust.module_drug.drug.AllDrugActivity
import com.meditrust.module_drug.workroom.message.MessageListActivity
import dagger.Component


/**
 * @author: create by zhongchao.wang
 * @date: 2019/10/12
 * @desc:
 */

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ActivityComponent {

    fun inject(allDrugActivity: AllDrugActivity)

    fun inject(messageListActivity: MessageListActivity)

}