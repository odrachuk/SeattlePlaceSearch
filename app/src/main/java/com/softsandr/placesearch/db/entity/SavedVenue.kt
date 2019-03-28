package com.softsandr.placesearch.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
@Entity
data class SavedVenue(
    @ColumnInfo(name = "venue_id")
    var venueId: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Int = 0

    var date: Long = System.currentTimeMillis()

    @Ignore
    constructor(venueId: String, date: Long): this(venueId) {
        this.date = date
    }
}