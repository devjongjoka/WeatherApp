package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Hour (
    @SerializedName("time")
    val time: String,
    @SerializedName("temp_f")
    val temperature: Int,
    @SerializedName("condition")
    val condition:Condition

)