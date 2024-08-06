package com.example.cloudkitchens.presentation.features.ordershelf

import com.example.cloudkitchens.domain.model.Order

/**
 * Comparator for sorting [Order] instances by state and timestamp.
 */

object OrderStateTimestampComparator : Comparator<Order> {

    /**
     * Compares two orders based on their state and the timestamp of their first change log entry.
     *
     * @param o1 The first order.
     * @param o2 The second order.
     * @return A negative integer if the first order should be sorted before the second, zero if they are equal, or a positive integer if the first order should be sorted after the second.
     */
    override fun compare(o1: Order, o2: Order): Int {
        return when {
            o1.state != o2.state -> o1.state.ordinal.compareTo(o2.state.ordinal)
            else -> {
                val timestamp1 = o1.changeLog.firstOrNull()?.timestamp ?: System.currentTimeMillis()
                val timestamp2 = o2.changeLog.firstOrNull()?.timestamp ?: System.currentTimeMillis()
                timestamp1.compareTo(timestamp2)
            }
        }
    }
}
