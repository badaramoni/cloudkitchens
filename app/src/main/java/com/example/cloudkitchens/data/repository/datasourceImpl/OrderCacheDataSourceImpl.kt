package com.example.cloudkitchens.data.repository.datasourceImpl

import com.example.cloudkitchens.data.model.OrderEvent
import com.example.cloudkitchens.data.repository.datasource.OrderCacheDataSource

/**
 * Implementation of [OrderCacheDataSource] for managing cached order data.
 */
class OrderCacheDataSourceImpl : OrderCacheDataSource {
    private var orderCache = ArrayList<OrderEvent>()

    /**
     * Retrieves a list of [OrderEvent] from the cache.
     *
     * @return A list of [OrderEvent].
     */
    override suspend fun getOrderFromCache(): List<OrderEvent> {
        return orderCache
    }

    /**
     * Saves a list of [OrderEvent] to the cache.
     *
     * @param orderEvent The list of [OrderEvent] to save.
     */
    override suspend fun saveOrderToCache(orderEvent: List<OrderEvent>) {
        orderCache = ArrayList(orderEvent)
    }

    /**
     * Deletes all [OrderEvent] from the cache.
     */
   /* override suspend fun deleteAll() {
        orderCache.clear()
    }*/
}
