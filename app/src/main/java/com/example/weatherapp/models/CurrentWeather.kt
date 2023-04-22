package com.example.weatherapp.models

data class CurrentWeather(
    //name of city
    var name: String,
    //region is "state"
    var region:String,
    var country:String,
    var temp_f:Double,
    //summary of weather in text
    var text:String,
    //percentage of humidity
    var humidity:Int,
    //wind speed
    var wind: Double,

    var iconURL:String?

    ) {
}