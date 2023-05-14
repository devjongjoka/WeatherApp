package com.example.weatherapp.ui.Components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.example.weatherapp.models.Hour
import com.example.weatherapp.ui.WeatherViewModel

@Composable
fun HourlyRow(
    hourlyWeather: Hour,
    vm:WeatherViewModel
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = hourlyWeather.time.substring(11,16))
        Text(text = "${hourlyWeather.temperature}°")
        Box(
            contentAlignment = Alignment.Center
        ) {
            var icon by remember {
                mutableStateOf<Bitmap?>(null)
            }
            LaunchedEffect(key1 = hourlyWeather.condition.iconURL) {
                icon = vm.fetchImage(hourlyWeather.condition.iconURL)
            }
            if (icon == null){
                CircularProgressIndicator()
            }else{
                Image(
                    modifier = Modifier.size(50.dp, 50.dp),
                    bitmap = icon!!.asImageBitmap(),
                    contentDescription = ""
                )
            }
        }
    }
}