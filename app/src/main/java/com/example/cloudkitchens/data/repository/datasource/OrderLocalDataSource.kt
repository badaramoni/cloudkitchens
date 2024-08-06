package com.example.cloudkitchens.data.repository.datasource

import com.example.cloudkitchens.data.model.OrderEvent

/**
 * Interface for managing local order data.
 */
interface OrderLocalDataSource {

    /**
     * Retrieves a list of OrderEvent from the local database.
     *
     * @return A list of OrderEvent.
     */
    suspend fun getOrderFromDB(): List<OrderEvent>

    /**
     * Saves a list of OrderEvent to the local database.
     *
     * @param orderEvent The list of OrderEvent to save.
     */
    suspend fun saveOrderToDB(orderEvent: List<OrderEvent>)

    /**
     * Deletes all OrderEvent from the local database.
     */
    suspend fun deleteAll()
}
