package com.divyansh.wanderpilot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.divyansh.wanderpilot.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    companion object {
        private const val API_KEY = "6d1f33003b2afd1a9d717b682d6fd36b"
    }

    private val _temperature = MutableStateFlow("Loading...")
    val temperature: StateFlow<String> = _temperature

    private val _cityName = MutableStateFlow("")
    val cityName: StateFlow<String> = _cityName

    private val _location = MutableStateFlow("")
    val location: StateFlow<String> = _location

    fun getWeather(city: String) {

        viewModelScope.launch {

            try {

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

                val response = RetrofitInstance.weatherApi.getWeather(
                    city = searchCity,
                    apiKey = API_KEY
                )

                if (response.isSuccessful && response.body() != null) {

                    val weather = response.body()!!

                    _cityName.value = weather.name
                    _location.value = weather.name

                    _temperature.value =
                        "${weather.main.temp} °C"

                    Log.d(
                        "Weather",
                        "City: ${weather.name}"
                    )

                    Log.d(
                        "Weather",
                        "Temp: ${weather.main.temp}"
                    )

                } else {

                    Log.e(
                        "Weather",
                        "HTTP ${response.code()}"
                    )

                    _temperature.value = "City not found"
                    _location.value = city

                }

            } catch (e: Exception) {

                Log.e(
                    "Weather",
                    "Exception",
                    e
                )

                _temperature.value = "Network Error"
                _location.value = city

            }

        }

    }

}