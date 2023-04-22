package com.example.weatherapp.Networking

import android.graphics.Bitmap
import com.example.weatherapp.models.CurrentWeather


interface  WeatherFetchI{

    suspend fun getWeather(): CurrentWeather;
    suspend fun getIcon(url:String ) : Bitmap?;

}

class WeatherFetcher {

}