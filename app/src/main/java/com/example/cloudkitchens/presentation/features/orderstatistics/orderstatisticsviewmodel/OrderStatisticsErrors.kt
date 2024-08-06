package com.example.cloudkitchens.presentation.features.orderstatistics.orderstatisticsviewmodel

/**
 * Represents various errors that can occur while fetching or processing order statistics.
 */
sealed class OrderStatisticsError {

    /**
     * Represents a network error.
     */
    object NetworkError : OrderStatisticsError()

    /**
     * Represents an error with the data.
     */
    object DataError : OrderStatisticsError()

    /**
     * Represents an unknown error.
     */
    object Unknown : OrderStatisticsError()
}
