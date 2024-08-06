package com.example.cloudkitchens.presentation.di.core

import android.content.Context
import com.example.cloudkitchens.presentation.di.orderdetails.OrderDetailsSubComponent
import com.example.cloudkitchens.presentation.di.ordershelf.OrderShelfSubComponent
import com.example.cloudkitchens.presentation.di.statistics.StatisticsSubComponent
import com.tps.challenge.presentation.di.order.OrderSubComponent
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(subcomponents = [
    OrderSubComponent::class,
    StatisticsSubComponent::class,
    OrderShelfSubComponent::class,
    OrderDetailsSubComponent::class
])
class AppModule (private val context: Context) {

    @Provides
    @Singleton
    fun getApplicationContext(): Context {
        return context.applicationContext
    }
}
