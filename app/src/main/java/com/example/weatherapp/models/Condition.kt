package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("text")
    //summary of weather in text
    val text:String,
    @SerializedName("icon")
    val iconURL:String
)