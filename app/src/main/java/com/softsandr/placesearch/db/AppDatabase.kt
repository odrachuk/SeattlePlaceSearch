package com.softsandr.placesearch.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.softsandr.placesearch.db.dao.SavedVenuesDao
import com.softsandr.placesearch.db.entity.SavedVenue

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
@Database(entities = [SavedVenue::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedVenuesDao(): SavedVenuesDao
}