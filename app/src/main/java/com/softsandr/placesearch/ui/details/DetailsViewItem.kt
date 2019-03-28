package com.softsandr.placesearch.ui.details

import com.softsandr.placesearch.api.Venue
import com.softsandr.placesearch.ui.search.SearchListItem

/**
 * Created by Oleksandr Drachuk on 28.03.19.
 */
data class DetailsViewItem(
    val id: String,
    val name: String,
    val category: String?,
    val distance: String?,
    val imageUrl: String?,
    val linkUrl: String?,
    val address: String?
) {
    companion object {

        @JvmStatic
        fun buildFromVenue(venue: Venue) : DetailsViewItem {
            return DetailsViewItem(venue.id,
                venue.name,
                venue.categories?.firstOrNull()?.name,
                SearchListItem.parseDistance(venue),
                SearchListItem.parseImageUrl(venue),
                venue.canonicalUrl,
                venue.location?.address
            )
        }
    }
}