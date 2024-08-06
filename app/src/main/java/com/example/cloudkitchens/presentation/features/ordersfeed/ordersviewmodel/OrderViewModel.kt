package com.example.cloudkitchens.presentation.features.ordersfeed.ordersviewmodel

import androidx.lifecycle.*
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.model.OrderCounts
import com.example.cloudkitchens.domain.model.ShelfType
import com.example.cloudkitchens.domain.usecase.GetOrderUseCase
import com.example.cloudkitchens.domain.usecase.UpdateOrderUseCase
import com.example.cloudkitchens.utils.Logger
import com.example.cloudkitchens.utils.AndroidLogger
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * ViewModel for managing orders and their updates.
 *
 * @param getOrderUseCase Use case for fetching initial orders.
 * @param updateOrderUseCase Use case for updating orders.
 */
class OrderViewModel(
    private val getOrderUseCase: GetOrderUseCase,
    private val updateOrderUseCase: UpdateOrderUseCase,
    private val logger: Logger = AndroidLogger() // Default to AndroidLogger
) : ViewModel() {

    private val _orders = MutableLiveData<List<Order>>()
    val orders: LiveData<List<Order>> = _orders

    private val _orderCounts = MutableLiveData<OrderCounts>()
    val orderCounts: LiveData<OrderCounts> = _orderCounts

    init {
        startPollingForUpdates()  // Start fetching immediately
    }

    /**
     * Starts polling for order updates.
     */
    fun startPollingForUpdates() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    // Fetch initial orders using getOrderUseCase
                    getOrderUseCase.execute().onSuccess { initialOrders ->
                        _orders.value = initialOrders
                        _orderCounts.value = calculateOrderCounts(initialOrders)

                        // Now start polling for updates with updateOrderUseCase
                        while (isActive) {
                            updateOrderUseCase.execute().onSuccess { updatedOrders ->
                                _orders.value = updatedOrders
                                _orderCounts.value = calculateOrderCounts(updatedOrders)
                            }.onFailure { error ->
                                // Handle errors gracefully (e.g., show error message)
                                // Log the error for debugging
                                logger.e("OrderViewModel", "Error updating orders: ${error.message}")
                            }

                            delay(2000) // Poll every 2 seconds
                        }
                    }.onFailure { error ->
                        // Handle errors gracefully (e.g., show error message)
                        // Log the error for debugging
                        logger.e("OrderViewModel", "Error fetching initial orders: ${error.message}")
                    }

                } catch (e: Exception) {
                    // Handle any unexpected exceptions
                    // Log the exception for debugging
                    logger.e("OrderViewModel", "Unexpected error: ${e.message}")
                }
            }
        }
    }

    /**
     * Calculates the counts of orders based on their shelf type.
     *
     * @param orders The list of orders.
     * @return The calculated [OrderCounts].
     */
    private fun calculateOrderCounts(orders: List<Order>): OrderCounts {
        return OrderCounts(
            all = orders.size,
            hot = orders.count { it.shelf == ShelfType.HOT },
            cold = orders.count { it.shelf == ShelfType.COLD },
            frozen = orders.count { it.shelf == ShelfType.FROZEN },
            overflow = orders.count { it.shelf == ShelfType.OVERFLOW }
        )
    }
}
