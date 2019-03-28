package com.softsandr.placesearch.ui.search

import com.softsandr.placesearch.api.Venue
import com.softsandr.placesearch.utils.DistanceUnit
import com.softsandr.placesearch.utils.DistanceUtil
import com.softsandr.placesearch.utils.roundOffDecimal

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
data class SearchListItem(val id: String,
                          val name: String,
                          val category: String?,
                          val address: String?,
                          val distance: String?,
                          val imageUrl: String?,
                          var saved: Boolean = false) {

    companion object {
        private const val IMAGE_DEF_SIZE = 100

        @JvmStatic
        fun buildFromVenue(venue: Venue) : SearchListItem {
            return SearchListItem(venue.id,
                venue.name,
                venue.categories?.firstOrNull()?.name,
                venue.location?.address,
                parseDistance(venue),
                parseImageUrl(venue),
                venue.saved)
        }

        @JvmStatic
        fun parseDistance(venue: Venue) : String? {
            return venue.location?.let {
                DistanceUtil.distance(SearchActivity.SEARCH_LOCATION_LAT, SearchActivity.SEARCH_LOCATION_LNG,
                    it.lat, it.lng).roundOffDecimal().toString() + " " + DistanceUnit.MILES.unit
            }
        }

        @JvmStatic
        fun parseImageUrl(venue: Venue) : String? {
            return venue.categories?.firstOrNull()?.let { it.icon?.prefix + IMAGE_DEF_SIZE + it.icon?.suffix }
        }
    }
}