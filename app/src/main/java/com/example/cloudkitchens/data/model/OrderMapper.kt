package com.example.cloudkitchens.data.model

import com.example.cloudkitchens.domain.model.Order
import com.example.cloudkitchens.domain.model.OrderChange
import com.example.cloudkitchens.domain.model.OrderState
import com.example.cloudkitchens.domain.model.ShelfType


/**
 * Mapper class for converting between OrderEvent data model and Order domain model.
 */

class OrderMapper {

    /**
     * Maps an OrderEvent DTO to the domain Order model.
     *
     * @param dto The OrderEvent data transfer object.
     * @return The mapped Order domain model.
     */


    fun mapToDomain(dto: OrderEvent): Order {
        val stateChanges = dto.stateChanges?.map { change ->
            OrderChange(change.timestamp, OrderState.valueOf(change.state), ShelfType.valueOf(change.shelf))
        }?.toMutableList() ?: mutableListOf()

        return Order(
            id = dto.id,
            price = dto.price ?: 0,
            item = dto.item ?: "",
            customer = dto.customer ?: "",
            destination = dto.destination ?: "",
            state = OrderState.valueOf(dto.state ?: "CREATED"),
            shelf = ShelfType.valueOf(dto.shelf ?: "NONE"),
            idealShelf = ShelfType.valueOf(dto.idealShelf ?: "NONE"),
            changeLog = stateChanges
        )
    }

    /**
     * Updates an existing Order domain model with new data from an OrderEvent DTO.
     *
     * @param existingOrder The existing Order domain model.
     * @param dto The new OrderEvent data transfer object.
     * @return The updated Order domain model.
     */

    fun updateOrder(existingOrder: Order, dto: OrderEvent): Order {
        val newChange = OrderChange(
            dto.timestamp ?: System.currentTimeMillis(),
            OrderState.valueOf(dto.state ?: "CREATED"),
            ShelfType.valueOf(dto.shelf ?: "NONE")
        )

        // Check if the change already exists
        if (!existingOrder.changeLog.any { it.timestamp == newChange.timestamp && it.state == newChange.state && it.shelf == newChange.shelf }) {
            existingOrder.apply {
                state = newChange.state
                shelf = newChange.shelf
                changeLog.add(newChange)
            }
        }

        return existingOrder
    }

    /**
     * Maps a domain Order model to an OrderEvent DTO.
     *
     * @param order The Order domain model.
     * @return The mapped OrderEvent data transfer object.
     */

    fun mapToData(order: Order): OrderEvent {
        return OrderEvent(
            id = order.id,
            price = order.price,
            item = order.item,
            customer = order.customer,
            destination = order.destination,
            state = order.state.name,
            shelf = order.shelf.name,
            idealShelf = order.idealShelf.name,
            timestamp = order.changeLog.lastOrNull()?.timestamp,
            stateChanges = order.changeLog.map { change ->
                OrderChangeDto(change.timestamp, change.state.name, change.shelf.name)
            }
        )
    }
}
