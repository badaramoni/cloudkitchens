package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.data.db.OrderDao
import com.example.cloudkitchens.data.repository.datasource.OrderLocalDataSource
import com.example.cloudkitchens.data.repository.datasourceImpl.OrderLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun provideLocal(orderDao: OrderDao):OrderLocalDataSource{
        return OrderLocalDataSourceImpl(orderDao)
    }

}