package com.example.cloudkitchens.data.repository.datasource

import com.example.cloudkitchens.data.model.OrderEvent

/**
 * Interface for managing cached order data.
 */
interface OrderCacheDataSource {

    /**
     * Retrieves a list of OrderEvent from the cache.
     *
     * @return A list of OrderEvent.
     */
    suspend fun getOrderFromCache(): List<OrderEvent>

    /**
     * Saves a list of OrderEvent to the cache.
     *
     * @param orderEvent The list of OrderEvent to save.
     */
    suspend fun saveOrderToCache(orderEvent: List<OrderEvent>)

    /**
     * Deletes all OrderEvent from the cache.
     */
    // suspend fun deleteAll()
}
