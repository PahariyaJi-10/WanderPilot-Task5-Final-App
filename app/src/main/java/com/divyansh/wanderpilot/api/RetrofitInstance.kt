package com.divyansh.wanderpilot.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val WEATHER_BASE_URL =
        "https://api.open-meteo.com/"

    private const val GEOCODING_BASE_URL =
        "https://geocoding-api.open-meteo.com/"

    val weatherApi: WeatherApiService by lazy {

        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }

    val geocodingApi: GeocodingApiService by lazy {

        Retrofit.Builder()
            .baseUrl(GEOCODING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }
}