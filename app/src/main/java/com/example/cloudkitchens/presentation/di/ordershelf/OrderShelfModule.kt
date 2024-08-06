package com.example.cloudkitchens.presentation.di.ordershelf

import com.example.cloudkitchens.domain.usecase.ShelfOrdersUseCase
import com.example.cloudkitchens.presentation.features.ordershelf.shelfordersviewmodel.ShelfOrderViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class OrderShelfModule {

    @OrderShelfScope
    @Provides
    fun provideOrderShelf(
        shelfOrdersUseCase: ShelfOrdersUseCase
    ): ShelfOrderViewModelFactory {
        return ShelfOrderViewModelFactory(
            shelfOrdersUseCase
        )
    }
}