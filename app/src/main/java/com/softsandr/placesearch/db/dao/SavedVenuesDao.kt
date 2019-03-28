package com.softsandr.placesearch.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.softsandr.placesearch.db.entity.SavedVenue
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
@Dao
abstract class SavedVenuesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(savedVenue: SavedVenue)

    @Query("DELETE FROM SavedVenue WHERE venue_id = :venueId")
    abstract fun delete(venueId: String): Int

    @Query("SELECT * FROM SavedVenue WHERE venue_id = :venueId ORDER BY date DESC LIMIT 1")
    abstract fun selectSavedVenue(venueId: String): SavedVenue?

    @Query("SELECT * FROM SavedVenue")
    abstract fun selectAll(): List<SavedVenue>

    fun deleteItem(venueId: String) : Completable = Completable.fromAction { delete(venueId) }
    fun insertItem(savedVenue: SavedVenue) : Completable = Completable.fromAction { insert(savedVenue) }
    fun selectItem(venueId: String) : Single<Boolean> = Maybe.fromCallable { selectSavedVenue(venueId) }.isEmpty
    fun selectAllItems() : Single<List<SavedVenue>> = Single.fromCallable { selectAll() }
}