package com.example.cloudkitchens.data.repository.datasourceImpl

import com.example.cloudkitchens.data.api.Service
import com.example.cloudkitchens.data.model.OrderEvent
import com.example.cloudkitchens.data.repository.datasource.OrderRemoteDataSource
import retrofit2.Response

/**
 * Implementation of [OrderRemoteDataSource] for managing remote order data.
 *
 * @param service The service for accessing remote order data.
 */
class OrderRemoteDataSourceImpl(private val service: Service) : OrderRemoteDataSource {

    /**
     * Retrieves a list of [OrderEvent] from the remote data source.
     *
     * @return A [Response] object containing a list of [OrderEvent].
     */
    override suspend fun getOrderEvents(): Response<List<OrderEvent>> {
        return service.getOrderEvent()
    }
}
