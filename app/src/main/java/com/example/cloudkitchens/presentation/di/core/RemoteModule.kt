package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.data.api.Service
import com.example.cloudkitchens.data.repository.datasource.OrderRemoteDataSource
import com.example.cloudkitchens.data.repository.datasourceImpl.OrderRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RemoteModule {

    @Singleton
    @Provides
    fun provideRemote(service: Service):OrderRemoteDataSource{
        return OrderRemoteDataSourceImpl(service)
    }

}