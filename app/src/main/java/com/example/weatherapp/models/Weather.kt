package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("location")
    val location :Location,
    @SerializedName("current")
    val current: CurrentWeather,
    @SerializedName("forecast")
    val forecast: Forecast
)