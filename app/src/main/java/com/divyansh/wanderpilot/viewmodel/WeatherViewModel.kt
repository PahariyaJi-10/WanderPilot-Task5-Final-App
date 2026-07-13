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

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    fun getWeather(city: String) {

        viewModelScope.launch {

            try {

                // Map famous destinations to searchable cities
                val searchCity = when (city.trim().lowercase()) {

                    "goa" -> "Panaji"
                    "kerala" -> "Thiruvananthapuram"
                    "rajasthan" -> "Jaipur"
                    "himachal" -> "Shimla"
                    "himachal pradesh" -> "Shimla"
                    "uttarakhand" -> "Dehradun"
                    "kashmir" -> "Srinagar"

                    else -> city
                }

                val geoResponse =
                    RetrofitInstance.geocodingApi.searchCity(searchCity)

                if (!geoResponse.isSuccessful ||
                    geoResponse.body()?.results.isNullOrEmpty()
                ) {

                    _temperature.value = "City not found"
                    _location.value = city
                    return@launch
                }

                val place = geoResponse.body()!!.results!![0]

                Log.d(
                    "WeatherSearch",
                    "Selected: ${place.name}, ${place.country}"
                )

                _cityName.value = place.name

                _location.value =
                    when (city.trim().lowercase()) {

                        "goa" -> "Goa, India"

                        "kerala" -> "Kerala, India"

                        "rajasthan" -> "Rajasthan, India"

                        "himachal",
                        "himachal pradesh" ->
                            "Himachal Pradesh, India"

                        "uttarakhand" ->
                            "Uttarakhand, India"

                        "kashmir" ->
                            "Kashmir, India"

                        else -> {
                            if (!place.country.isNullOrEmpty())
                                "${place.name}, ${place.country}"
                            else
                                place.name
                        }
                    }

                val weatherResponse =
                    RetrofitInstance.weatherApi.getWeather(
                        latitude = place.latitude,
                        longitude = place.longitude
                    )

                if (weatherResponse.isSuccessful &&
                    weatherResponse.body() != null
                ) {

                    val weather =
                        weatherResponse.body()!!.current_weather

                    _temperature.value =
                        "${weather.temperature} °C"

                } else {

                    _temperature.value = "Failed to load weather"

                    Log.e(
                        "WeatherAPI",
                        "HTTP ${weatherResponse.code()}"
                    )
                }

            } catch (e: Exception) {

                Log.e("WeatherAPI", "Exception", e)

                _temperature.value =
                    e.localizedMessage ?: "Network Error"

                _location.value = city
            }
        }
    }
}