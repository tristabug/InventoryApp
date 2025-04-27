package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Delete
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    // Manual Query - returns all columns, as Flows, from the Item table in ascending order
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>

    // Manual Query - select all columns from Items where the id matches the :id argument
    @Query("SELECT * from items WHERE id = :id")

    // Takes an int argument and returns a Flow<Item>
    fun getItem(id: Int): Flow<Item> // Flow - as a return type notifies us whenever the data in the database changes.

    // Insert Method - inserts an entity into the database
    @Insert(onConflict = OnConflictStrategy.IGNORE) // onConflict - specifies what to do when an item in the database has conflicting requests on the same item
    suspend fun insert(item: Item)

    // Update Method - updates the entity of the primary key that's passed to it
    @Update
    suspend fun update(item: Item)

    // Delete Method - deletes the entities passed to it
    @Delete
    suspend fun delete(item: Item)
}