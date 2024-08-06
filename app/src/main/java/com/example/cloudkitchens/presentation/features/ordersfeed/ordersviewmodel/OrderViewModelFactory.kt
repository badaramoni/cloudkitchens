package com.example.cloudkitchens.presentation.features.ordersfeed.ordersviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloudkitchens.domain.usecase.GetOrderUseCase
import com.example.cloudkitchens.domain.usecase.UpdateOrderUseCase

/**
 * Factory for creating [OrderViewModel] instances.
 *
 * @param getOrderUseCase The use case for retrieving orders.
 * @param updateOrderUseCase The use case for updating orders.
 */
class OrderViewModelFactory(
    private val getOrderUseCase: GetOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass A `Class` whose instance is requested.
     * @return A newly created ViewModel instance.
     * @throws IllegalArgumentException if the given `modelClass` is not assignable from `OrderViewModel`.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(getOrderUseCase, updateOrderUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
