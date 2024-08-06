package com.tps.challenge.presentation.di.store

import com.example.cloudkitchens.domain.usecase.GetOrderUseCase
import com.example.cloudkitchens.domain.usecase.UpdateOrderUseCase
import com.example.cloudkitchens.presentation.features.ordersfeed.ordersviewmodel.OrderViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class OrderModule {

    @OrderScope
    @Provides
    fun provideOrderViewModelFactory(
        getOrderUseCase: GetOrderUseCase,
        updateOrderUseCase: UpdateOrderUseCase,
    ): OrderViewModelFactory {
        return OrderViewModelFactory(
           getOrderUseCase,
            updateOrderUseCase
        )
    }
}