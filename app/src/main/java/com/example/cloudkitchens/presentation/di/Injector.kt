package com.example.cloudkitchens.presentation.di

import com.example.cloudkitchens.presentation.di.orderdetails.OrderDetailsSubComponent
import com.example.cloudkitchens.presentation.di.ordershelf.OrderShelfSubComponent
import com.example.cloudkitchens.presentation.di.statistics.StatisticsSubComponent
import com.tps.challenge.presentation.di.order.OrderSubComponent


interface Injector {
    fun createOrderSubComponent(): OrderSubComponent
    fun createStatisticSubComponent():StatisticsSubComponent
    fun createOrderShelfSubComponent():OrderShelfSubComponent
    fun createOrderDetailsSubComponent():OrderDetailsSubComponent
}