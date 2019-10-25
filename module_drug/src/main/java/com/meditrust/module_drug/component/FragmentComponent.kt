package com.meditrust.module_drug.component

import com.meditrust.module_base.component.AppComponent
import com.meditrust.module_base.scope.FragmentScope
import com.meditrust.module_drug.order.fragment.*
import com.meditrust.module_drug.pharmacy.MyPharmacyFragment
import com.meditrust.module_drug.welfare.WelfareFragment
import dagger.Component


/**
 * @author Yang Shihao
 * @date 2018/12/7
 */

@FragmentScope
@Component(dependencies = [AppComponent::class])
interface FragmentComponent {
    fun inject(myPharmacyFragment: MyPharmacyFragment)

    fun inject(allOrderFragment: AllOrderFragment)

    fun inject(unAuditOrderFragment: UnAuditOrderFragment)

    fun inject(unDeliveryOrderFragment: UnDeliveryOrderFragment)

    fun inject(deliveredOrderFragment: DeliveredOrderFragment)

    fun inject(completedOrderFragment: CompletedOrderFragment)

    fun inject(welfareFragment: WelfareFragment)
}