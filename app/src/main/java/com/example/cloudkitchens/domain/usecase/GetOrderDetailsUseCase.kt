package com.example.cloudkitchens.domain.usecase

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.repository.OrderRepository

/**
 * Use case for retrieving order details.
 *
 * @param orderRepository The repository for managing orders.
 */
class GetOrderDetailsUseCase(private val orderRepository: OrderRepository) {

    /**
     * Executes the use case to get order details by order ID.
     *
     * @param orderId The ID of the order.
     * @return A [Result] containing the [Order] if successful, or an exception if failed.
     */
    suspend fun execute(orderId: String): Result<Order> {
        return try {
            Result.success(orderRepository.getOrderById(orderId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
