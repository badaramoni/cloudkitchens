package com.example.cloudkitchens.presentation.features.ordershelf.shelfordersviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.usecase.ShelfOrdersUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ShelfOrdersViewModel(
    private val shelfOrdersUseCase: ShelfOrdersUseCase
) : ViewModel() {

    private val _orders = MutableLiveData<Map<String, List<Order>>>()
    val orders: LiveData<Map<String, List<Order>>> = _orders

    init {
        startPollingForUpdates()
    }

    private fun startPollingForUpdates() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    val allOrders = mutableMapOf<String, List<Order>>()
                    fetchOrdersForShelf("HOT")?.let { allOrders["HOT"] = it }
                    fetchOrdersForShelf("COLD")?.let { allOrders["COLD"] = it }
                    fetchOrdersForShelf("FROZEN")?.let { allOrders["FROZEN"] = it }
                    fetchOrdersForShelf("OVERFLOW")?.let { allOrders["OVERFLOW"] = it }

                    // Combine all orders for the "All" shelf type
                    allOrders["All"] = allOrders.values.flatten()

                    _orders.postValue(allOrders)
                } catch (e: Exception) {
                    // Handle any unexpected exceptions
                }

                delay(2000) // Poll every 2 seconds
            }
        }
    }

    private suspend fun fetchOrdersForShelf(shelfType: String): List<Order>? {
        return shelfOrdersUseCase.execute(shelfType).getOrNull()
    }

    fun getOrders(shelfType: String): LiveData<List<Order>> {
        val result = MutableLiveData<List<Order>>()
        _orders.observeForever { ordersMap ->
            result.value = ordersMap[shelfType] ?: emptyList()
        }
        return result
    }
}
