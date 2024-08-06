package com.example.cloudkitchens.data.repository.datasourceImpl

import com.example.cloudkitchens.data.db.OrderDao
import com.example.cloudkitchens.data.model.OrderEvent
import com.example.cloudkitchens.data.repository.datasource.OrderLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of [OrderLocalDataSource] for managing local order data.
 *
 * @param orderDao The DAO for accessing order data.
 */
class OrderLocalDataSourceImpl(private val orderDao: OrderDao) : OrderLocalDataSource {

    /**
     * Retrieves a list of [OrderEvent] from the local database.
     *
     * @return A list of [OrderEvent].
     */
    override suspend fun getOrderFromDB(): List<OrderEvent> {
        return withContext(Dispatchers.IO) {
            orderDao.getOrdersFromDB()
        }
    }

    /**
     * Saves a list of [OrderEvent] to the local database.
     *
     * @param orderEvent The list of [OrderEvent] to save.
     */
    override suspend fun saveOrderToDB(orderEvent: List<OrderEvent>) {
        withContext(Dispatchers.IO) {
            orderDao.saveOrdersTodDB(orderEvent)
        }
    }

    /**
     * Retrieves a list of [OrderEvent] from the local database by shelf type.
     *
     * @param shelfType The type of shelf to filter orders by.
     * @return A list of [OrderEvent].
     */

    /**
     * Deletes all [OrderEvent] from the local database.
     */
    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            orderDao.deleteAll()
        }
    }
}
