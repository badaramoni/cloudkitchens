package com.example.cloudkitchens.data.repository.datasourceImpl

/**
 * Exception thrown when there are no orders available.
 *
 * @constructor Creates an [EmptyOrdersException] with a default message.
 */
class EmptyOrdersException : Exception("No orders available at this time.")
