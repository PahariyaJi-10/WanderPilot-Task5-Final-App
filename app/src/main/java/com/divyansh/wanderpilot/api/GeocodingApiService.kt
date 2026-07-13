package com.divyansh.wanderpilot.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {

    @GET("v1/search")
    suspend fun searchCity(
        @Query("name") city: String,
        @Query("count") count: Int = 1
    ): Response<GeocodingResponse>
}