package com.divyansh.wanderpilot.viewmodel

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

                if (response.isSuccessful && response.body() != null) {

                    val temp =
                        response.body()!!.current_weather.temperature

                    _temperature.value = "$temp °C"

                } else {

                    _temperature.value = "Failed to load weather"
                }

            } catch (e: Exception) {

                _temperature.value = "Network Error"
            }
        }
    }
}