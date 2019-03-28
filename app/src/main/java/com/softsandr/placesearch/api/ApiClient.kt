package com.softsandr.placesearch.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Oleksandr Drachuk on 27.03.19.
 */
interface ApiClient {

    @GET("/v2/venues/search")
    fun search(@Query("near") near: String,
               @Query("query") query: String,
               @Query("limit") limit: Int,
               @Query("client_id") clientId: String = API_CLIENT_ID,
               @Query("client_secret") clientSecret: String = API_CLIENT_SECRET,
               @Query("v") version: String = API_CLIENT_VERSION): Single<SearchApiResponse>

    @GET("/v2/venues/{id}")
    fun details(@Path("id") id: String,
                @Query("client_id") clientId: String = API_CLIENT_ID,
                @Query("client_secret") clientSecret: String = API_CLIENT_SECRET,
                @Query("v") version: String = API_CLIENT_VERSION): Single<DetailsApiResponse>

    companion object {
        const val API_CLIENT_URL = "https://api.foursquare.com"
        const val API_CLIENT_ID = "NYYNE5I5VZJQWXLXGT53XDWLW2DSBD2AL54VQMADZYDQAA23"
        const val API_CLIENT_SECRET = "0Y4NLX2WSOE5E0TGBDXSDBZDKCUNKXZHO5RXI0VNGLNVIPO5"
        const val API_CLIENT_VERSION = "20180401"
    }
}