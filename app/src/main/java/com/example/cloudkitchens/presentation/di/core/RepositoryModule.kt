package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.data.model.OrderMapper
import com.example.cloudkitchens.data.repository.OrderRepositoryImpl
import com.example.cloudkitchens.data.repository.datasource.OrderCacheDataSource
import com.example.cloudkitchens.data.repository.datasource.OrderLocalDataSource
import com.example.cloudkitchens.data.repository.datasource.OrderRemoteDataSource
import com.example.cloudkitchens.domain.repository.OrderRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
      orderRemoteDataSource: OrderRemoteDataSource,
      orderLocalDataSource: OrderLocalDataSource,
      orderCacheDataSource: OrderCacheDataSource,
      orderMapper: OrderMapper
      ):OrderRepository{
        return OrderRepositoryImpl(
            orderRemoteDataSource,
            orderLocalDataSource,
            orderCacheDataSource,
            orderMapper
        )
    }

    @Singleton
    @Provides
    fun provideOrderMapper(): OrderMapper {
        return OrderMapper()
    }

}