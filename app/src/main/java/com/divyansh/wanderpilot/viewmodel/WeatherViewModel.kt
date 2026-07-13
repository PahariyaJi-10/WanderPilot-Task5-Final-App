package com.divyansh.wanderpilot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyansh.wanderpilot.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _temperature = MutableStateFlow("Loading...")
    val temperature: StateFlow<String> = _temperature

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    fun getWeather(city: String) {

        viewModelScope.launch {

            try {

                // Step 1: Search city
                val geoResponse =
                    RetrofitInstance.geocodingApi.searchCity(city)

                if (!geoResponse.isSuccessful || geoResponse.body()?.results.isNullOrEmpty()) {

                    _temperature.value = "City not found"
                    return@launch
                }

                val location = geoResponse.body()!!.results!![0]

                _cityName.value = location.name

                // Step 2: Get weather using coordinates
                val weatherResponse =
                    RetrofitInstance.weatherApi.getWeather(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )

                if (weatherResponse.isSuccessful && weatherResponse.body() != null) {

                    val weather = weatherResponse.body()!!.current_weather

                    _temperature.value =
                        "${weather.temperature} °C"

                } else {

                    _temperature.value =
                        "Failed to load weather"

                    Log.e(
                        "WeatherAPI",
                        "HTTP ${weatherResponse.code()}"
                    )
                }

            } catch (e: Exception) {

                Log.e("WeatherAPI", "Exception", e)

                _temperature.value =
                    e.localizedMessage ?: "Network Error"
            }
        }
    }
}