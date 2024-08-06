package com.example.cloudkitchens.presentation.di.core

import com.example.cloudkitchens.data.db.AppDatabase
import android.content.Context
import androidx.room.Room
import com.example.cloudkitchens.data.db.OrderDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDataBaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context):AppDatabase{
        return Room.databaseBuilder(context,AppDatabase::class.java,"cloudKitchen")
            .build()
    }

    @Singleton
    @Provides
    fun provideOrderDao(appDatabase: AppDatabase):OrderDao{
        return appDatabase.getOrderDao()
    }
}