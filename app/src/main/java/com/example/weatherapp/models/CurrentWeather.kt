package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temp_f")
    val temp_f:Double,
    @SerializedName("humidity")
    //percentage of humidity
    val humidity:Int,
    @SerializedName("air_quality")
    //air quality
    val air: Air,
    @SerializedName("condition")
    val condition : Condition
) {
}