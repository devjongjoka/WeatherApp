package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("maxtemp_f")
    val maxTemp: Int,
    @SerializedName("mintemp_f")
    val minTemp:Int,
    @SerializedName("condition")
    val condition: Condition
)
