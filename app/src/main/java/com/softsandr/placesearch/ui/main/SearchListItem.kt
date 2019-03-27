package com.softsandr.placesearch.ui.main

import com.softsandr.placesearch.api.ApiResponse

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
data class SearchListItem(val name: String,
                          val category: String,
                          val address: String,
                          val distance: String,
                          val imageUrl: String) {

    companion object {
        private const val IMAGE_DEF_SIZE = 100

        @JvmStatic
        fun parseImageUrl(venue: ApiResponse.ResponseBody.Venue) : String? {
            return venue.categories?.firstOrNull()?.let { it.icon?.prefix + IMAGE_DEF_SIZE + it.icon?.suffix }
        }
    }
}