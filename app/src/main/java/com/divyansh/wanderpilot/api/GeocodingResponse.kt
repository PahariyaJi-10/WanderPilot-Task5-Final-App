package com.divyansh.wanderpilot.api

data class GeocodingResponse(
    val results: List<Location>?
)

data class Location(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String?
)