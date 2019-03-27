package com.softsandr.placesearch.api

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
data class ApiResponse(
    val meta: ResponseMeta,
    val response: ResponseBody
) {
    data class ResponseMeta(
        val code: Int,
        val requestId: String
    )

    data class ResponseBody(
        val venues: List<Venue>
    ) {
        data class Venue(
            val id: String,
            val name: String,
            val contact: Contact,
            val location: Location
        ) {
            data class Contact(
                val phone: String,
                val formattedPhone: String,
                val twitter: String
            )

            data class Location(
                val address: String,
                val crossStreet: String,
                val lat: Double,
                val lng: Double,
                val postalCode: String,
                val cc: String,
                val city: String,
                val state: String,
                val country: String
            )
        }
    }
}