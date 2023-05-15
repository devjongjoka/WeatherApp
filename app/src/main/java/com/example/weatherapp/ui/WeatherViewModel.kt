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

class WeatherViewModel(app: Application): AndroidViewModel(app) {

    private val _current : MutableState<Weather?>  = mutableStateOf(null)
    val current : State<Weather?> = _current

    val locations = mutableStateOf<List<savedLocation>>(emptyList())

    private val _waiting: MutableState<Boolean>
    val waiting: State<Boolean>

    var _zip = "21014"

    var _repository: ILocationsRepository = LocationsDatabaseRepository(app = getApplication())
    private val _fetcher = WeatherFetcher()

    init{
        _waiting = mutableStateOf(false)
        waiting = _waiting
        viewModelScope.launch {
            _waiting.value = true
            locations.value = _repository.getLocations()
            _current.value = _fetcher.getWeather(_zip)
            _waiting.value = false
        }
    }

    fun addLocation(savedLocation: savedLocation){
        viewModelScope.launch {
            _repository.addLocation(savedLocation)
            locations.value = _repository.getLocations()
        }
    }

    suspend fun deleteLocation(savedLocation: savedLocation){
        viewModelScope.launch {
            _repository.deleteLocation(savedLocation)
            locations.value = _repository.getLocations()
        }
    }

    suspend fun getLocations() : List<savedLocation> {
        return _repository.getLocations()
    }


    suspend fun fetchImage(url:String): Bitmap? {
        return _fetcher.getIcon("http:$url")
    }

    fun searchWeather(zipCode: String) {
        _zip = zipCode
        viewModelScope.launch {
            _current.value = _fetcher.getWeather(_zip)
        }
    }
}