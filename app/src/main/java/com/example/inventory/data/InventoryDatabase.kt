package com.example.inventory.data

import androidx.room.Database
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.Room

// Provides instances of the defined DAOs to the app
class InventoryDatabase {
    // Version - must be incremented whenever a schema change is made
    // exportSchema - specifies whether to keep schema version history backups
    @Database(entities = [Item::class], version = 1, exportSchema = false)
    abstract class InventoryDatabase : RoomDatabase() {
        // Abstract - returns the ItemDao so the database is aware of it
        abstract fun itemDao(): ItemDao

        // Companion - allows access to the methods to create or get the database, using the class name as a qualifier
        companion object {
            // Instance Var - keeps a reference to the database, maintaining a single instance of the database opened at a one time
            // Volatile - prevents it's variable from being cached, forcing all reads and writes to/from the main memory. This keeps the Instance up-to-date.
            @Volatile
            private var Instance: InventoryDatabase? = null

            fun getDatabase(context: Context): InventoryDatabase {
                // Synchronized - avoids the race condition which is when there are multiple database instance requests, resulting in multiple databases.
                return Instance ?: synchronized(this) {
                    // databaseBuilder - gets the database
                    Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                        /**
                         * Setting this option in your app's database builder means that Room
                         * permanently deletes all data from the tables in your database when it
                         * attempts to perform a migration with no defined migration path.
                         */
                        .fallbackToDestructiveMigration()
                        .build() // creates the database instance
                        .also { Instance = it } // keeps a reference to the recently created database instance
                }
            }
        }
    }

}