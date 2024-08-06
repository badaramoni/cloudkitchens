package com.example.cloudkitchens.data.db

import androidx.room.TypeConverter
import com.example.cloudkitchens.data.model.OrderChangeDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Provides type converters for Room to handle non-primitive data types.
 */

class ConvertersOrder {

    /**
     * Converts a list of [OrderChangeDto] objects to a JSON string for storage in the database.
     *
     * @param value The list of OrderChangeDto objects.
     * @return The JSON string representation of the list.
     */

    @TypeConverter
    fun fromOrderChangeDtoList(value: List<OrderChangeDto>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<OrderChangeDto>>() {}.type
        return gson.toJson(value, type)
    }

    /**
     * Converts a JSON string to a list of [OrderChangeDto] objects when retrieving from the database.
     *
     * @param value The JSON string representation of the list.
     * @return The list of OrderChangeDto objects.
     */

    @TypeConverter
    fun toOrderChangeDtoList(value: String): List<OrderChangeDto>? {
        val gson = Gson()
        val type = object : TypeToken<List<OrderChangeDto>>() {}.type
        return gson.fromJson(value, type)
    }
}
