package com.example.cloudkitchens.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.cloudkitchens.data.db.ConvertersOrder
import com.google.gson.annotations.SerializedName


/**
 * Represents an order event in the system.
 *
 * @property id Unique identifier for the order.
 * @property customer Name of the customer.
 * @property destination Delivery destination for the order.
 * @property idealShelf Ideal shelf for the order.
 * @property item Item being ordered.
 * @property price Price of the order item.
 * @property shelf Current shelf of the order.
 * @property state Current state of the order.
 * @property timestamp Timestamp of the order event.
 * @property stateChanges List of state changes for the order.
 */

@Entity(tableName = "order")
data class OrderEvent(
    @PrimaryKey
    @SerializedName("id")
    val id: String,
    @SerializedName("customer")
    val customer: String?,
    @SerializedName("destination")
    val destination: String?,
    @SerializedName("idealShelf")
    val idealShelf: String?,
    @SerializedName("item")
    val item: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("shelf")
    val shelf: String?,
    @SerializedName("state")
    val state: String?,
    @SerializedName("timestamp")
    val timestamp: Long?,
    @TypeConverters(ConvertersOrder::class)
    val stateChanges: List<OrderChangeDto>? = emptyList()
)

/**
 * Represents a change in the order's state.
 *
 * @property timestamp Timestamp of the state change.
 * @property state New state of the order.
 * @property shelf Shelf associated with the state change.
 */
data class OrderChangeDto(
    val timestamp: Long,
    val state: String,
    val shelf: String
)
