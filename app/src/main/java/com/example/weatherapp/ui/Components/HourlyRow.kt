package com.example.weatherapp.ui.Components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.models.Hour
import com.example.weatherapp.ui.WeatherViewModel

@Composable
fun HourlyRow(
    hourlyWeather: Hour,
    vm:WeatherViewModel
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(170.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = hourlyWeather.time.substring(11,16))
            Text(text = "${hourlyWeather.temperature}°F")
            Box(
                contentAlignment = Alignment.Center
            ) {
                var icon by remember {
                    mutableStateOf<Bitmap?>(null)
                }
                LaunchedEffect(key1 = hourlyWeather.condition.iconURL) {
                    if(!hourlyWeather.condition.iconURL.equals("")) {
                        icon = vm.fetchImage(hourlyWeather.condition.iconURL)
                    }
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
}