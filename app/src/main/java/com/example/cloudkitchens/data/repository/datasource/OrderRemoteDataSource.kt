package com.example.cloudkitchens.data.repository.datasource

import com.example.cloudkitchens.data.model.OrderEvent
import retrofit2.Response

/**
 * Interface for managing remote order data.
 */
interface OrderRemoteDataSource {

    /**
     * Retrieves a list of OrderEvent from the remote data source.
     *
     * @return A Response object containing a list of OrderEvent.
     */
    suspend fun getOrderEvents(): Response<List<OrderEvent>>
}
