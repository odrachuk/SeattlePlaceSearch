package com.softsandr.placesearch.api

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
data class ApiResponseMeta(
    val code: Int,
    val requestId: String
)

data class SearchApiResponse(
    val meta: ApiResponseMeta,
    val response: SearchResponseBody
) {
    data class SearchResponseBody(
        val venues: List<Venue>
    )
}

data class DetailsApiResponse(
    val meta: ApiResponseMeta,
    val response: DetailsResponseBody
) {
    data class DetailsResponseBody(
        val venue: Venue
    )
}

data class Venue(
    val id: String,
    val name: String,
    val canonicalUrl: String?,
    val contact: Contact?,
    val location: Location?,
    val categories: List<Category>?
) {
    data class Contact(
        val phone: String,
        val formattedPhone: String
    )

    data class Location(
        val address: String,
        val crossStreet: String?,
        val lat: Double,
        val lng: Double,
        val postalCode: String,
        val cc: String,
        val city: String,
        val state: String,
        val country: String
    )

    data class Category(
        val id: String,
        val name: String,
        val icon: CategoryIcon?
    ) {
        data class CategoryIcon(
            val prefix: String,
            val suffix: String
        )
    }
}