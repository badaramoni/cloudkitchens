package com.example.cloudkitchens.presentation.features.ordersfeed.ordersviewmodel

/**
 * Represents various errors that can occur while fetching or processing orders.
 */
sealed class OrderError {

    /**
     * Represents a network error.
     */
    object NetworkError : OrderError()

    /**
     * Represents a database error.
     */
    object DatabaseError : OrderError()

    /**
     * Represents a cache error.
     */
    object CacheError : OrderError()

    /**
     * Represents an error where an order is not found.
     *
     * @property orderId The ID of the order that was not found.
     */
    data class OrderNotFound(val orderId: String) : OrderError()

    /**
     * Represents an unknown error.
     */
    object UnknownError : OrderError()
}
