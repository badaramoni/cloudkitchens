package com.example.cloudkitchens.presentation.di.orderdetails

import com.example.cloudkitchens.presentation.features.orderdetails.OrderDetailFragment
import dagger.Subcomponent

@OrderDetailsScope
@Subcomponent(modules = [OrderDetailsModule::class])
interface OrderDetailsSubComponent {
    fun inject(orderDetailFragment: OrderDetailFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create():OrderDetailsSubComponent
    }
}