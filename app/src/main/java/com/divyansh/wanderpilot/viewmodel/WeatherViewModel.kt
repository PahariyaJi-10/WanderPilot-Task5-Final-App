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

    fun getWeather() {

        viewModelScope.launch {

            try {

                val response = RetrofitInstance.api.getWeather(
                    latitude = 15.2993,
                    longitude = 74.1240
                )

                if (response.isSuccessful) {

                    val weather = response.body()

                    if (weather != null) {

                        _temperature.value =
                            "${weather.current_weather.temperature} °C"

                    } else {

                        _temperature.value = "No weather data"

                        Log.e("WeatherAPI", "Response body is null")
                    }

                } else {

                    _temperature.value =
                        "HTTP ${response.code()}"

                    Log.e(
                        "WeatherAPI",
                        "HTTP ${response.code()} : ${response.message()}"
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