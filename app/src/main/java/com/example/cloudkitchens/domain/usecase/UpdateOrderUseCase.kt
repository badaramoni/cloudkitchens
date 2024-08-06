package com.example.cloudkitchens.domain.usecase

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.repository.OrderRepository

/**
 * Use case for updating orders.
 *
 * @param orderRepository The repository for managing orders.
 */
class UpdateOrderUseCase(private val orderRepository: OrderRepository) {

    /**
     * Executes the use case to update orders.
     *
     * @return A [Result] containing a list of updated [Order] if successful, or an exception if failed.
     */
    suspend fun execute(): Result<List<Order>> {
        return try {
            Result.success(orderRepository.updateOrder())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
