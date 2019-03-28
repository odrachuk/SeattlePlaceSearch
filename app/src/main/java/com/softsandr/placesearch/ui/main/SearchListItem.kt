package com.softsandr.placesearch.ui.main

import com.softsandr.placesearch.api.ApiResponse
import com.softsandr.placesearch.utils.DistanceUnit
import com.softsandr.placesearch.utils.DistanceUtil
import com.softsandr.placesearch.utils.roundOffDecimal

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
data class SearchListItem(val name: String,
                          val category: String,
                          val address: String,
                          val distance: String,
                          val imageUrl: String?) {

    companion object {
        private const val IMAGE_DEF_SIZE = 100

        @JvmStatic
        fun buildFromVenue(venue: ApiResponse.ResponseBody.Venue) : SearchListItem {
            return SearchListItem(venue.name,
                venue.categories?.firstOrNull()?.name ?: "N/A", // for the simplicity sake we use hardcoded constant "N/A"
                venue.location?.address ?: "N/A",
                parseDistance(venue) ?: "N/A",
                parseImageUrl(venue))
        }

        private fun parseDistance(venue: ApiResponse.ResponseBody.Venue) : String? {
            return venue.location?.let {
                DistanceUtil.distance(MainActivity.SEARCH_LOCATION_LAT, MainActivity.SEARCH_LOCATION_LNG,
                    it.lat, it.lng).roundOffDecimal().toString() + " " + DistanceUnit.MILES.unit
            }
        }

        private fun parseImageUrl(venue: ApiResponse.ResponseBody.Venue) : String? {
            return venue.categories?.firstOrNull()?.let { it.icon?.prefix + IMAGE_DEF_SIZE + it.icon?.suffix }
        }
    }
}