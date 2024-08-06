package com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudkitchens.domain.model.OrderStatistics
import com.example.cloudkitchens.domain.usecase.GetOrderStatisticsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive
import java.io.IOException

/**
 * Represents the view state for order statistics.
 *
 * @param statistics The order statistics.
 * @param isLoading Whether the statistics are being loaded.
 * @param error The error, if any, that occurred while loading statistics.
 */
data class OrderStatisticsViewState(
    val statistics: OrderStatistics? = null,
    val isLoading: Boolean = false,
    val error: OrderStatisticsError? = null
)

/**
 * ViewModel for managing order statistics.
 *
 * @param getOrderStatisticsUseCase The use case for retrieving order statistics.
 */
class OrderStatisticsViewModel(
    private val getOrderStatisticsUseCase: GetOrderStatisticsUseCase
) : ViewModel() {

    private val _viewState = MutableLiveData(OrderStatisticsViewState())
    val viewState: LiveData<OrderStatisticsViewState> = _viewState

    init {
        startPollingForUpdates()
    }

    /**
     * Starts polling for order statistics updates.
     */
    private fun startPollingForUpdates() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    _viewState.value = _viewState.value?.copy(isLoading = true)
                    getOrderStatisticsUseCase.execute().onSuccess { newStatistics ->
                        val currentStatistics = _viewState.value?.statistics ?: OrderStatistics()
                        _viewState.value = _viewState.value?.copy(
                            statistics = mergeStatistics(currentStatistics, newStatistics),
                            isLoading = false,
                            error = null // Clear any previous errors
                        )
                    }.onFailure { error ->
                        _viewState.value = _viewState.value?.copy(
                            isLoading = false,
                            error = mapError(error)
                        )
                    }
                } catch (e: Exception) {
                    // Log the exception for debugging
                    // Log.e("OrderStatisticsViewModel", "Unexpected error: ${e.message}")
                    _viewState.value = _viewState.value?.copy(
                        isLoading = false,
                        error = OrderStatisticsError.Unknown
                    )
                }

                delay(2000) // Poll every 2 seconds
            }
        }
    }

    /**
     * Merges the current and new order statistics.
     *
     * @param current The current order statistics.
     * @param new The new order statistics.
     * @return The merged order statistics.
     */
    private fun mergeStatistics(current: OrderStatistics, new: OrderStatistics?): OrderStatistics {
        return if (new == null) {
            current
        } else {
            OrderStatistics(
                totalSales = new.totalSales,
                totalWaste = new.totalWaste,
                totalRevenue = new.totalRevenue,
                ordersTrashed = new.ordersTrashed,
                ordersDelivered = new.ordersDelivered
            )
        }
    }

    /**
     * Maps an error to an [OrderStatisticsError].
     *
     * @param error The error to map.
     * @return The corresponding [OrderStatisticsError].
     */
    private fun mapError(error: Throwable): OrderStatisticsError {
        return when (error) {
            is IOException -> OrderStatisticsError.NetworkError
            else -> OrderStatisticsError.DataError
        }
    }
}
