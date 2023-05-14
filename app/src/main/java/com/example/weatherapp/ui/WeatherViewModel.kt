package com.example.weatherapp.ui

import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
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

    var zip = "21014"

    private val _fetcher = WeatherFetcher()

    init{
        _waiting = mutableStateOf(false)
        waiting = _waiting
        viewModelScope.launch {
            _waiting.value = true
            _current.value = _fetcher.getWeather(zip)
            _waiting.value = false
        }
    }

    suspend fun fetchImage(url:String): Bitmap? {
        return _fetcher.getIcon("http:$url")
    }

    fun searchWeather(zipCode: String) {
        zip = zipCode
        viewModelScope.launch {
            val weather = _fetcher.getWeather(zip)
            _current.value = weather
        }
    }
}