package com.softsandr.placesearch.utils

import com.softsandr.placesearch.api.Venue

/**
 * Created by Oleksandr Drachuk on 29.03.19.
 */

internal fun stubbedVenue(id: String) = Venue (
    id, "Metroplex Town Cars",
    "https://foursquare.com/v/metroplex-town-cars/5ad1648a1987ec57f4601764",
    Venue.Contact("4252986295", "(425) 298-6295"),
    Venue.Location(
        "32200 Military Rd S Apt B301",
        null,
        47.60372380771074,
        -122.33453392982481,
        "98001", "US", "Federal Way", "WA", "United States"
    ),
    listOf(
        Venue.Category(
            "54541b70498ea6ccd0204bff",
            "Transportation Service",
            Venue.Category.CategoryIcon(
                "https://ss3.4sqi.net/img/categories_v2/travel/taxi_",
                ".png"
            )
        )
    )
)