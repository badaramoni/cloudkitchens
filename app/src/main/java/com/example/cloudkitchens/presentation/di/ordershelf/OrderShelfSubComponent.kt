package com.example.cloudkitchens.presentation.di.ordershelf

import com.example.cloudkitchens.presentation.features.ordershelf.ShelfFragment
import dagger.Subcomponent


@OrderShelfScope
@Subcomponent(modules = [OrderShelfModule::class])
interface OrderShelfSubComponent {
    fun inject(shelfFragment: ShelfFragment)

    @Subcomponent.Factory
    interface Factory{
        fun create():OrderShelfSubComponent
    }


}