package com.example.cloudkitchens.presentation.di.core


import com.example.cloudkitchens.presentation.di.orderdetails.OrderDetailsSubComponent
import com.example.cloudkitchens.presentation.di.ordershelf.OrderShelfSubComponent
import com.example.cloudkitchens.presentation.di.statistics.StatisticsSubComponent
import com.tps.challenge.presentation.di.order.OrderSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules =
   [AppModule::class,
    NetworkModule::class,
    CacheModule::class,
    RemoteModule::class,
    LocalModule::class,
    AppDataBaseModule::class,
    RepositoryModule::class,
    UseCaseModule::class])
interface AppComponent {

    fun storeSubcomponent(): OrderSubComponent.Factory
    fun statisticsSubComponent(): StatisticsSubComponent.Factory
    fun orderdetailsSubComponent(): OrderDetailsSubComponent.Factory
    fun ordershelfSubComponent():OrderShelfSubComponent.Factory

}
