package com.example.cloudkitchens.data.repository

import android.util.Log
import com.example.cloudkitchens.data.model.OrderChangeDto
import com.example.cloudkitchens.data.model.OrderEvent
import com.example.cloudkitchens.data.model.OrderMapper
import com.example.cloudkitchens.data.repository.datasource.OrderCacheDataSource
import com.example.cloudkitchens.data.repository.datasource.OrderLocalDataSource
import com.example.cloudkitchens.data.repository.datasource.OrderRemoteDataSource
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Implementation of the [OrderRepository] interface.
 *
 * @property orderRemoteDataSource The remote data source for fetching orders.
 * @property orderLocalDataSource The local data source for fetching orders.
 * @property orderCacheDataSource The cache data source for caching orders.
 * @property orderMapper The mapper for converting data models to domain models.
 */
class OrderRepositoryImpl(
    private val orderRemoteDataSource: OrderRemoteDataSource,
    private val orderLocalDataSource: OrderLocalDataSource,
    private val orderCacheDataSource: OrderCacheDataSource,
    private val orderMapper: OrderMapper
) : OrderRepository {

    /**
     * Fetches the list of orders.
     *
     * @return A list of orders.
     */
    override suspend fun getOrder(): List<Order> {
        return getOrderFromCache().map { orderMapper.mapToDomain(it) }
    }

    /**
     * Updates the list of orders.
     *
     * @return An updated list of orders.
     */
    override suspend fun updateOrder(): List<Order> {
        val newOrderList = getOrdersFromApi()
        mergeOrders(newOrderList)
        return getOrderFromCache().map { orderMapper.mapToDomain(it) }
    }

    /**
     * Fetches an order by its ID.
     *
     * @param orderId The ID of the order.
     * @return The order with the specified ID.
     */
    override suspend fun getOrderById(orderId: String): Order {
        val orderEvent = getOrderFromCache().find { it.id == orderId }
            ?: throw Exception("Order not found")
        return orderMapper.mapToDomain(orderEvent)
    }

    /**
     * Fetches orders by their shelf.
     *
     * @param shelf The shelf of the orders.
     * @return A list of orders on the specified shelf.
     */
    override suspend fun getShelfItems(shelf: String): List<Order> {
        return getOrderFromCache()
            .filter { it.idealShelf == shelf }
            .map { orderMapper.mapToDomain(it) }
    }

    /**
     * Fetches order statistics.
     *
     * @return A list of order statistics.
     */
    override suspend fun getOrderStatistics(): List<Order> {
        return getOrderFromCache().map { orderMapper.mapToDomain(it) }
    }

    /**
     * Fetches the list of orders from the API.
     *
     * @return A list of order events fetched from the API.
     */
    private suspend fun getOrdersFromApi(): List<OrderEvent> {
        var orderList: List<OrderEvent> = emptyList()
        try {
            val response = orderRemoteDataSource.getOrderEvents()
            val body: List<OrderEvent>? = response.body()
            if (body != null) {
                orderList = body
            }
        } catch (e: Exception) {
            Log.i("OrderRepositoryImpl", e.message.toString())
        }
        return orderList
    }

    /**
     * Fetches the list of orders from the database.
     *
     * @return A list of order events fetched from the database.
     */
    private suspend fun getOrderFromDB(): List<OrderEvent> {
        var orderList: List<OrderEvent> = emptyList()
        try {
            val ordersFromDB = orderLocalDataSource.getOrderFromDB()
            orderList = ordersFromDB
        } catch (e: Exception) {
            Log.i("OrderRepositoryImpl", e.message.toString())
        }
        return if (orderList.isNotEmpty()) {
            orderList
        } else {
            orderList = getOrdersFromApi()
            orderLocalDataSource.saveOrderToDB(orderList)
            orderCacheDataSource.saveOrderToCache(orderList)
            orderList
        }
    }

    /**
     * Fetches the list of orders from the cache.
     *
     * @return A list of order events fetched from the cache.
     */
    private suspend fun getOrderFromCache(): List<OrderEvent> {
        var orderList: List<OrderEvent> = emptyList()
        try {
            val cachedOrders = orderCacheDataSource.getOrderFromCache()
            if (cachedOrders.isNotEmpty()) {
                orderList = cachedOrders
            } else {
                orderList = getOrderFromDB()
                orderLocalDataSource.saveOrderToDB(orderList)
                orderCacheDataSource.saveOrderToCache(orderList)
            }
        } catch (e: Exception) {
            Log.i("OrderRepositoryImpl", e.message.toString())
        }
        return orderList
    }

    /**
     * Merges the new orders with the existing orders.
     *
     * @param newOrders The list of new orders to be merged.
     */
    private suspend fun mergeOrders(newOrders: List<OrderEvent>) {
        withContext(Dispatchers.IO) {
            val existingOrders = orderCacheDataSource.getOrderFromCache().toMutableList()

            newOrders.forEach { newOrder ->
                val existingOrderIndex = existingOrders.indexOfFirst { it.id == newOrder.id }
                if (existingOrderIndex != -1) {
                    val existingOrder = existingOrders[existingOrderIndex]
                    val stateChanges = (existingOrder.stateChanges.orEmpty() + OrderChangeDto(
                        timestamp = newOrder.timestamp ?: System.currentTimeMillis(),
                        state = newOrder.state ?: "CREATED",
                        shelf = newOrder.shelf ?: "NONE"
                    )).distinctBy { it.timestamp }

                    val updatedOrder = orderMapper.updateOrder(
                        existingOrder = orderMapper.mapToDomain(existingOrder.copy(stateChanges = stateChanges)),
                        dto = newOrder
                    )
                    existingOrders[existingOrderIndex] = orderMapper.mapToData(updatedOrder)
                } else {
                    val newStateChanges = listOf(OrderChangeDto(
                        timestamp = newOrder.timestamp ?: System.currentTimeMillis(),
                        state = newOrder.state ?: "CREATED",
                        shelf = newOrder.shelf ?: "NONE"
                    ))
                    existingOrders.add(newOrder.copy(stateChanges = newStateChanges))
                }
            }

            orderCacheDataSource.saveOrderToCache(existingOrders)
        }
    }
}
