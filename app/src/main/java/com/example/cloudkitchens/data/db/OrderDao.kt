package com.example.cloudkitchens.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.cloudkitchens.data.model.OrderEvent
/**
 * Data Access Object for interacting with [OrderEvent] entities in the database.
 */
@Dao
interface OrderDao {

    /**
     * Saves a list of [OrderEvent] objects to the database, replacing any existing entries with the same primary key.
     *
     * @param orderEvent The list of OrderEvent objects to save.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrdersTodDB(orderEvent: List<OrderEvent>)

    /**
     * Retrieves all [OrderEvent] objects from the database.
     *
     * @return A list of all OrderEvent objects in the database.
     */
    @Query("SELECT * FROM `order`")
    suspend fun getOrdersFromDB(): List<OrderEvent>


    /**
     * Retrieves [OrderEvent] objects from the database by shelf type.
     *
     * @param shelfType The type of shelf to filter orders by.
     * @return A list of OrderEvent objects with the specified shelf type.
     */

    /**
     * Deletes all [OrderEvent] objects from the database.
     */
    @Query("DELETE FROM `order`")
    suspend fun deleteAll()
}