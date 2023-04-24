package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("name")
    //name of city
    val name: String,
    @SerializedName("region")
    //region is "state"
    val region:String,
    @SerializedName("country")
    val country:String
)