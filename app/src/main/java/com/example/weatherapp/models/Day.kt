package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_f")
    val maxTemp: Double,
    @SerializedName("mintemp_f")
    val minTemp:Double,
    @SerializedName("condition")
    val condition: Condition
)
