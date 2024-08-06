package com.example.cloudkitchens.presentation.features.orderdetails.orderdetailsviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.usecase.GetOrderDetailsUseCase
import kotlinx.coroutines.launch

/**
 * ViewModel for managing order details.
 *
 * @param getOrderDetailsUseCase The use case for retrieving order details.
 */
class OrderDetailsViewModel(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase
) : ViewModel() {

    private val _orderDetails = MutableLiveData<OrderDetailsViewState>()
    val orderDetails: LiveData<OrderDetailsViewState> = _orderDetails

    /**
     * Fetches the details of an order by its ID.
     *
     * @param orderId The ID of the order to fetch details for.
     */
    fun fetchOrderDetails(orderId: String) {
        viewModelScope.launch {
            _orderDetails.value = OrderDetailsViewState(isLoading = true)
            getOrderDetailsUseCase.execute(orderId).onSuccess { order ->
                _orderDetails.value = OrderDetailsViewState(order = order)
            }.onFailure { error ->
                _orderDetails.value = OrderDetailsViewState(error = mapError(error))
            }
        }
    }

    /**
     * Maps an error to an [OrderDetailsError].
     *
     * @param error The error to map.
     * @return The corresponding [OrderDetailsError].
     */
    private fun mapError(error: Throwable): OrderDetailsError {
        return when (error) {
            is NoSuchElementException -> OrderDetailsError.OrderNotFound
            else -> OrderDetailsError.UnknownError
        }
    }
}

/**
 * Represents the view state for order details.
 *
 * @param order The order details.
 * @param isLoading Whether the order details are being loaded.
 * @param error The error, if any, that occurred while loading order details.
 */
data class OrderDetailsViewState(
    val order: Order? = null,
    val isLoading: Boolean = false,
    val error: OrderDetailsError? = null
)

/**
 * Represents the possible errors that can occur while fetching order details.
 */
sealed class OrderDetailsError {
    object OrderNotFound : OrderDetailsError()
    object UnknownError : OrderDetailsError()
}
