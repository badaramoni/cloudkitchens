package com.example.cloudkitchens.domain.usecase

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.repository.OrderRepository

/**
 * Use case for retrieving orders from a specific shelf.
 *
 * @param orderRepository The repository for managing orders.
 */
class ShelfOrdersUseCase(private val orderRepository: OrderRepository) {

    /**
     * Executes the use case to get a list of orders from a specific shelf.
     *
     * @param shelfType The type of shelf.
     * @return A [Result] containing a list of [Order] if successful, or an exception if failed.
     */
    suspend fun execute(shelfType: String): Result<List<Order>> {
        return try {
            Result.success(orderRepository.getShelfItems(shelfType))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
