package com.example.cloudkitchens.presentation.di.orderdetails

import com.example.cloudkitchens.domain.usecase.GetOrderDetailsUseCase
import com.example.cloudkitchens.presentation.features.orderdetails.orderdetailsviewmodel.OrderDetailsViewModelFactory
import com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel.OrderStatisticsViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class OrderDetailsModule {

    @OrderDetailsScope
    @Provides
    fun provideOrderDetails(
        getOrderDetailsUseCase: GetOrderDetailsUseCase
    ): OrderDetailsViewModelFactory {
        return OrderDetailsViewModelFactory(getOrderDetailsUseCase)
    }

}