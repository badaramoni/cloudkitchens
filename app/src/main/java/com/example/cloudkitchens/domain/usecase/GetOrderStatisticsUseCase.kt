package com.example.cloudkitchens.domain.usecase

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.model.OrderState
import com.example.cloudkitchens.domain.model.OrderStatistics
import com.example.cloudkitchens.domain.repository.OrderRepository

/**
 * Use case for retrieving order statistics.
 *
 * @param orderRepository The repository for managing orders.
 */
class GetOrderStatisticsUseCase(private val orderRepository: OrderRepository) {

    /**
     * Executes the use case to get order statistics.
     *
     * @return A [Result] containing the [OrderStatistics] if successful, or an exception if failed.
     */
    suspend fun execute(): Result<OrderStatistics> {
        return try {
            val orders = orderRepository.getOrderStatistics()
            Result.success(calculateStatistics(orders))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Calculates statistics for a list of orders.
     *
     * @param orders The list of [Order].
     * @return The calculated [OrderStatistics].
     */
    private fun calculateStatistics(orders: List<Order>): OrderStatistics {
        val ordersTrashed = orders.count { it.state == OrderState.TRASHED }
        val ordersDelivered = orders.count { it.state == OrderState.DELIVERED }

        val totalSales = orders
            .filter { it.state == OrderState.DELIVERED }
            .sumOf { it.price }

        val totalWaste = orders
            .filter { it.state == OrderState.TRASHED }
            .sumOf { it.price }

        val totalRevenue = totalSales - totalWaste

        return OrderStatistics(
            totalSales = totalSales.toDouble(),
            totalWaste = totalWaste.toDouble(),
            totalRevenue = totalRevenue.toDouble(),
            ordersTrashed = ordersTrashed,
            ordersDelivered = ordersDelivered
        )
    }
}
