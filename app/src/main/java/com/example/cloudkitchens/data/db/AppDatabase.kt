package com.example.cloudkitchens.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cloudkitchens.data.model.OrderEvent

/**
 * The main database for the application.
 * This class uses Room to manage a local SQLite database.
 *
 * @constructor Creates a new instance of AppDatabase.
 */

@Database(entities = [OrderEvent::class], version = 1)
@TypeConverters(ConvertersOrder::class)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Returns the DAO for accessing order events.
     *
     * @return The DAO for accessing order events.
     */

    abstract fun getOrderDao(): OrderDao
}
