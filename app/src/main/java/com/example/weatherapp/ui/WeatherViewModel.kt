package com.example.weatherapp.ui

import androidx.lifecycle.ViewModel
import com.example.weatherapp.models.Daily
import com.example.weatherapp.models.Hour

class WeatherViewModel: ViewModel() {
    val weekDayList: List<Daily>
    val hourlyWeatherList: List<Hour>

    init{
        weekDayList = (0..6).map { i -> Daily("Monday", 60 + i, 40 + i, "Rain") }
        hourlyWeatherList = (0..20).map { i -> Hour("${i+1}:00", 60+i ) }
    }
}