package com.example.cloudkitchens.presentation.features.ordershelf.shelfordersviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cloudkitchens.domain.usecase.ShelfOrdersUseCase

/**
 * Factory for creating [ShelfOrdersViewModel] instances.
 *
 * @param shelfOrdersUseCase The use case for retrieving orders from different shelves.
 */
class ShelfOrderViewModelFactory(
    private val shelfOrdersUseCase: ShelfOrdersUseCase
) : ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     *
     * @param modelClass A `Class` whose instance is requested.
     * @return A newly created ViewModel instance.
     * @throws IllegalArgumentException if the given `modelClass` is not assignable from `ShelfOrdersViewModel`.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShelfOrdersViewModel::class.java)) {
            return ShelfOrdersViewModel(shelfOrdersUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}
