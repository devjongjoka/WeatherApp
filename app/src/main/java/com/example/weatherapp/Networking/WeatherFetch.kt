package com.example.weatherapp.Networking

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.weatherapp.models.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request


interface  WeatherFetchI{

    suspend fun getWeather(): Weather;
    suspend fun getIcon(url:String ) : Bitmap?;

}

class WeatherFetcher :WeatherFetchI{

    private val URL = "http://api.weatherapi.com/v1/forecast.json?key=b397bda724ef4ebf8a0204033230105&q=London&days=3&aqi=yes&alerts=yes"
    private val client = OkHttpClient()

    override suspend fun getWeather(): Weather{
        return withContext(Dispatchers.IO){

            val request = Request.Builder()
                .get()
                .url(URL)
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body

            if(responseBody != null){
                val jsonString = responseBody.string()
                Log.d("Tag",jsonString)
                val gson = Gson()
                val weatherType = object: TypeToken<Weather>(){}.type

                val  weather: Weather = gson.fromJson(jsonString, weatherType)

                weather
            }
            else {
                throw Exception ("Response is empty")
            }

        }
    }

    override suspend fun getIcon(weatherUrl: String): Bitmap? {
        return withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .get()
                .url(weatherUrl)
                .build()
            val response = client.newCall(request).execute()
            val responseBody = response.body
            if(responseBody != null) {
                val bytes = responseBody.byteStream()
                BitmapFactory.decodeStream(bytes)
            } else {
                null
            }
        }
    }

}