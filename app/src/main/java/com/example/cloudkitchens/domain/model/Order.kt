package com.example.cloudkitchens.domain.model

/**
 * Represents an order in the system.
 *
 * @property id Unique identifier for the order.
 * @property price Price of the order item.
 * @property item Item being ordered.
 * @property customer Name of the customer.
 * @property destination Delivery destination for the order.
 * @property state Current state of the order.
 * @property shelf Current shelf of the order.
 * @property idealShelf Ideal shelf for the order.
 * @property changeLog List of state changes for the order.
 */
data class Order(
    val id: String,
    val price: Int,
    val item: String,
    val customer: String,
    val destination: String,
    var state: OrderState,
    var shelf: ShelfType,
    val idealShelf: ShelfType,
    val changeLog: MutableList<OrderChange> = mutableListOf()
) {
    /**
     * Updates the state and shelf of the order.
     *
     * @param newState The new state of the order.
     * @param newShelf The new shelf of the order.
     * @return The updated order.
     */
    fun updateStateAndShelf(newState: OrderState, newShelf: ShelfType): Order {
        if (this.state != newState || this.shelf != newShelf) {
            this.changeLog.add(OrderChange(System.currentTimeMillis(), newState, newShelf))
        }
        return this.copy(state = newState, shelf = newShelf)
    }
}

/**
 * Represents a change in the order's state.
 *
 * @property timestamp Timestamp of the state change.
 * @property state New state of the order.
 * @property shelf Shelf associated with the state change.
 */
data class OrderChange(
    val timestamp: Long,
    val state: OrderState,
    val shelf: ShelfType
)

/**
 * Represents statistics for orders in the system.
 *
 * @property totalSales Total sales amount.
 * @property totalWaste Total waste amount.
 * @property totalRevenue Total revenue amount.
 * @property ordersTrashed Number of orders trashed.
 * @property ordersDelivered Number of orders delivered.
 */
data class OrderStatistics(
    val totalSales: Double = 0.0,
    val totalWaste: Double = 0.0,
    val totalRevenue: Double = 0.0,
    val ordersTrashed: Int = 0,
    val ordersDelivered: Int = 0
)

/**
 * Enum class representing the state of an order.
 */
enum class OrderState {
    CREATED, COOKING, WAITING, DELIVERED, TRASHED, CANCELLED
}

/**
 * Enum class representing the type of shelf.
 */
enum class ShelfType {
    NONE, HOT, COLD, FROZEN, OVERFLOW
}

/**
 * Represents counts of orders in various states.
 *
 * @property all Total count of all orders.
 * @property hot Count of hot orders.
 * @property cold Count of cold orders.
 * @property frozen Count of frozen orders.
 * @property overflow Count of overflow orders.
 */
data class OrderCounts(
    val all: Int,
    val hot: Int,
    val cold: Int,
    val frozen: Int,
    val overflow: Int
)
