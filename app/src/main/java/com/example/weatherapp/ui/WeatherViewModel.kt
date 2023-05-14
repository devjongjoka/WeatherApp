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

    private val _current : MutableState<Weather?>  = mutableStateOf(null)
    val current : State<Weather?> = _current

    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>

    private var _zip : String = "21014"

    private val _fetcher = WeatherFetcher()

//    val weekDayList: List<Daily>
//    val hourlyWeatherList: List<Hour>

    init{
        _waiting = mutableStateOf(false)
        waiting = _waiting
//        weekDayList = (0..2).map { i -> Daily("Monday", 60 + i, 40 + i, "Rain") }
//        hourlyWeatherList = (0..23).map { i -> Hour("${i+1}:00", 60.0+i, condition = Condition("","")) }
        viewModelScope.launch {
            _waiting.value = true
            _current.value = _fetcher.getWeather(_zip)
            _waiting.value = false
        }
    }

    suspend fun fetchImage(url:String): Bitmap? {
        return _fetcher.getIcon("http:$url")
    }

    fun searchWeather(zipCode: String) {
        _zip = zipCode
        viewModelScope.launch {
            val weather = _fetcher.getWeather(_zip)
            _current.value = weather
        }
    }
}