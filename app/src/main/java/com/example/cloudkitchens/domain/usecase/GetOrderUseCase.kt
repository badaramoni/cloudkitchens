package com.example.cloudkitchens.domain.usecase

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.repository.OrderRepository

/**
 * Use case for retrieving orders.
 *
 * @param orderRepository The repository for managing orders.
 */
class GetOrderUseCase(private val orderRepository: OrderRepository) {

    /**
     * Executes the use case to get a list of orders.
     *
     * @return A [Result] containing a list of [Order] if successful, or an exception if failed.
     */
    suspend fun execute(): Result<List<Order>> {
        return try {
            Result.success(orderRepository.getOrder())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
