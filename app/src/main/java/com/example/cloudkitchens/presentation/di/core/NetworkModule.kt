package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.data.api.Service
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides Network communication related instances.
 */
@Module
class NetworkModule(private val baseurl:String) {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS) // Increase timeout settings
            .readTimeout(30, TimeUnit.SECONDS) // Increase timeout settings
            .writeTimeout(30, TimeUnit.SECONDS) // Increase timeout settings
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseurl)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): Service {
        return retrofit.create(Service::class.java)
    }
}
