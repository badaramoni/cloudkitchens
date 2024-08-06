package com.example.cloudkitchens.presentation.di.statistics

import com.example.cloudkitchens.presentation.features.orderstatistics.OrderStatisticsFragment
import dagger.Subcomponent

@StatisticsScope
@Subcomponent(modules = [StatisticsModule::class])
interface StatisticsSubComponent {
    fun inject(orderStatisticsFragment: OrderStatisticsFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create():StatisticsSubComponent
    }
}