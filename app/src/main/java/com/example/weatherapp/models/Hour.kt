package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Hour (
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_f")
    val temperature: Double,
    @SerializedName("condition")
    val condition:Condition

)