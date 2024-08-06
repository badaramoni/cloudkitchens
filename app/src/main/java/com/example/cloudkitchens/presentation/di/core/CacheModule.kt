package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.data.repository.datasource.OrderCacheDataSource
import com.example.cloudkitchens.data.repository.datasourceImpl.OrderCacheDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun provideCache():OrderCacheDataSource{
        return OrderCacheDataSourceImpl()
    }
}