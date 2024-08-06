package com.example.cloudkitchens.domain.repository

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.model.OrderStatistics

/**
 * Repository interface for managing orders.
 */
interface OrderRepository {

   /**
    * Retrieves a list of orders.
    *
    * @return A list of [Order].
    */
   suspend fun getOrder(): List<Order>

   /**
    * Updates the list of orders.
    *
    * @return A list of updated [Order].
    */
   suspend fun updateOrder(): List<Order>

   /**
    * Retrieves an order by its ID.
    *
    * @param orderId The ID of the order.
    * @return The [Order] with the specified ID.
    */
   suspend fun getOrderById(orderId: String): Order

   /**
    * Retrieves hot shelf items.
    *
    * @param shelf The shelf type.
    * @return A list of [Order] from the specified shelf.
    */
   suspend fun getShelfItems(shelf: String): List<Order>

   /**
    * Retrieves order statistics.
    *
    * @return A list of [OrderStatistics].
    */
   suspend fun getOrderStatistics(): List<Order>
}
