package com.example.cloudkitchens.presentation

import android.app.Application
import com.example.cloudkitchens.BuildConfig
import com.example.cloudkitchens.presentation.di.Injector
import com.example.cloudkitchens.presentation.di.core.*
import com.example.cloudkitchens.presentation.di.orderdetails.OrderDetailsSubComponent
import com.example.cloudkitchens.presentation.di.ordershelf.OrderShelfSubComponent
import com.example.cloudkitchens.presentation.di.statistics.StatisticsSubComponent
import com.tps.challenge.presentation.di.order.OrderSubComponent

/**
 * The application class - an entry point into our app where we initialize Dagger.
 */
class Application : Application(), Injector {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
    }

    /**
     * Initializes the Dagger component.
     */
    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .networkModule(NetworkModule(BuildConfig.BASE_URL))
            .remoteModule(RemoteModule())
            .build()

        // Log the initialization
        // Log.d("Application", "Dagger initialized successfully")
    }

    override fun createOrderSubComponent(): OrderSubComponent {
        return appComponent.storeSubcomponent().create()
    }

    override fun createStatisticSubComponent(): StatisticsSubComponent {
        return appComponent.statisticsSubComponent().create()
    }

    override fun createOrderShelfSubComponent(): OrderShelfSubComponent {
        return appComponent.ordershelfSubComponent().create()
    }

    override fun createOrderDetailsSubComponent(): OrderDetailsSubComponent {
        return appComponent.orderdetailsSubComponent().create()
    }
}
