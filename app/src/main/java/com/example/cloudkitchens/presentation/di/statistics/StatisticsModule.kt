package com.example.cloudkitchens.presentation.di.statistics

import com.example.cloudkitchens.domain.usecase.GetOrderStatisticsUseCase
import com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel.OrderStatisticsViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class StatisticsModule {

    @StatisticsScope
    @Provides
    fun provideStatisticsModule(
        getOrderStatisticsUseCase: GetOrderStatisticsUseCase
    ): OrderStatisticsViewModelFactory {
        return OrderStatisticsViewModelFactory(getOrderStatisticsUseCase)
    }

}