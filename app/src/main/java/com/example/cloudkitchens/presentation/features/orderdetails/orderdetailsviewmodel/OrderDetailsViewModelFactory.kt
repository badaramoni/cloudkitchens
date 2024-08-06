package com.example.cloudkitchens.presentation.features.orderdetails.orderdetailsviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloudkitchens.domain.usecase.GetOrderDetailsUseCase

/**
 * Factory for creating [OrderDetailsViewModel] instances.
 *
 * @param getOrderDetailsUseCase The use case for retrieving order details.
 */
class OrderDetailsViewModelFactory(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass A `Class` whose instance is requested.
     * @return A newly created ViewModel instance.
     * @throws IllegalArgumentException if the given `modelClass` is not assignable from `OrderDetailsViewModel`.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderDetailsViewModel::class.java)) {
            return OrderDetailsViewModel(getOrderDetailsUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
