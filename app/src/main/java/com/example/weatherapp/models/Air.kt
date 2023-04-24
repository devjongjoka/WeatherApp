package com.example.weatherapp.models

import com.google.gson.annotations.SerializedName

data class Air(
    @SerializedName("us-epa-index")
    val index:Int
)