package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.domain.repository.OrderRepository
import com.example.cloudkitchens.domain.usecase.GetOrderDetailsUseCase
import com.example.cloudkitchens.domain.usecase.GetOrderStatisticsUseCase
import com.example.cloudkitchens.domain.usecase.GetOrderUseCase
import com.example.cloudkitchens.domain.usecase.ShelfOrdersUseCase
import com.example.cloudkitchens.domain.usecase.UpdateOrderUseCase
import dagger.Module
import dagger.Provides


@Module
class UseCaseModule {

    @Provides
    fun providegetorderModule(orderRepository: OrderRepository):GetOrderUseCase{
        return GetOrderUseCase(orderRepository)
    }

    @Provides
    fun provideupdateordereModule(orderRepository: OrderRepository):UpdateOrderUseCase{
        return UpdateOrderUseCase(orderRepository)
    }

    @Provides
    fun provideGetOrderById(orderRepository: OrderRepository):GetOrderDetailsUseCase{
        return GetOrderDetailsUseCase(orderRepository)
    }

    @Provides
    fun provideHotShelfItems(orderRepository: OrderRepository):ShelfOrdersUseCase{
        return ShelfOrdersUseCase(orderRepository)
    }

    @Provides
    fun providegetOrderStatistics(orderRepository: OrderRepository):GetOrderStatisticsUseCase{
        return GetOrderStatisticsUseCase(orderRepository)
    }
}