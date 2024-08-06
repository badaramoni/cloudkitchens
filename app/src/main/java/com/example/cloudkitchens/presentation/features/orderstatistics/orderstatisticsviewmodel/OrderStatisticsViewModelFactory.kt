package com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloudkitchens.domain.usecase.GetOrderStatisticsUseCase

/**
 * Factory for creating [OrderStatisticsViewModel] instances.
 *
 * @param getOrderStatisticsUseCase The use case for retrieving order statistics.
 */
class OrderStatisticsViewModelFactory(
    private val getOrderStatisticsUseCase: GetOrderStatisticsUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass A `Class` whose instance is requested.
     * @return A newly created ViewModel instance.
     * @throws IllegalArgumentException if the given `modelClass` is not assignable from `OrderStatisticsViewModel`.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderStatisticsViewModel::class.java)) {
            return OrderStatisticsViewModel(getOrderStatisticsUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
