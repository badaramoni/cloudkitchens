package com.tps.challenge.presentation.di.order

import com.example.chipnavigationapp.TabsFragment
import com.tps.challenge.presentation.di.store.OrderModule
import com.tps.challenge.presentation.di.store.OrderScope
import dagger.Subcomponent

@OrderScope
@Subcomponent(modules = [OrderModule::class])
interface OrderSubComponent {
   fun inject(tabsFragment: TabsFragment)


    @Subcomponent.Factory
    interface Factory{
        fun create():OrderSubComponent
    }
}