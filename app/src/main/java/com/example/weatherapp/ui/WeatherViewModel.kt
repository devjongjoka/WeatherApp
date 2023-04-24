package com.example.weatherapp.ui

import android.graphics.Bitmap
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Networking.WeatherFetcher
import com.example.weatherapp.models.*
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val _current : MutableState<Weather?>  = mutableStateOf(
        Weather(
            Location("New York", "", ""),
            CurrentWeather(temp_f = 80.0, humidity = 50, Air(index = 50), condition = Condition(text = "Sunshine", iconURL = "//cdn.weatherapi.com/weather/64x64/day/113.png"))
            )
        )

    val current : State<Weather?> = _current

    private val _fetcher = WeatherFetcher()


    val weekDayList: List<Daily>
    val hourlyWeatherList: List<Hour>


    init{
        weekDayList = (0..6).map { i -> Daily("Monday", 60 + i, 40 + i, "Rain") }
        hourlyWeatherList = (0..20).map { i -> Hour("${i+1}:00", 60+i ) }
        viewModelScope.launch {
            val weather = _fetcher.getWeather()
            _current.value = weather
        }
    }

    suspend fun fetchImage(): Bitmap? {
        return _fetcher.getIcon("http:" + _current.value?.current!!.condition.iconURL)
    }
}