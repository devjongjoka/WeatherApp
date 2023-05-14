package com.example.weatherapp.ui

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.Networking.WeatherFetcher
import com.example.weatherapp.data.ILocationsRepository
import com.example.weatherapp.data.impl.LocationsDatabaseRepository
import com.example.weatherapp.models.*
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel(app: Application): AndroidViewModel(app) {

    private val _current : MutableState<Weather?>  = mutableStateOf(null)
    val current : State<Weather?> = _current
    private val _savedLocations:MutableState<List<savedLocation>> = mutableStateOf(listOf())
    val savedLocations : State<List<savedLocation>> = _savedLocations
    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>

    var zip = "21014"
    private lateinit var _repository:ILocationsRepository
    private val _fetcher = WeatherFetcher()


    init{
        _waiting = mutableStateOf(false)
        waiting = _waiting
        viewModelScope.launch {
            _waiting.value = true
            _current.value = _fetcher.getWeather(zip)
            _repository = LocationsDatabaseRepository(getApplication())
            _savedLocations.value=_repository.getLocations()
            _waiting.value = false
        }
    }

    suspend fun fetchImage(url:String): Bitmap? {
        return _fetcher.getIcon("http:$url")
    }

    fun addLocation(savedLocation: savedLocation){
        viewModelScope.launch {
            _repository.addLocation(savedLocation)
            _savedLocations.value=_repository.getLocations()

        }
    }
    fun deleteLocation(savedLocation: savedLocation){
        viewModelScope.launch {
            _repository.deleteLocation(savedLocation)
            _savedLocations.value=_repository.getLocations()
        }
    }
    fun searchWeather(zipCode: String) {
        zip = zipCode
        viewModelScope.launch {
            val weather = _fetcher.getWeather(zip)
            _current.value = weather
        }
    }
}