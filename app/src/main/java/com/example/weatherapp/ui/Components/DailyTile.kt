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
import androidx.compose.ui.unit.sp
import com.example.weatherapp.models.ForecastDay
import com.example.weatherapp.ui.WeatherViewModel

@Composable
fun DailyTile(
    weekDay: ForecastDay,
    vm: WeatherViewModel,
    date: String
) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = date, fontSize = 16.sp)
        }
        Box(contentAlignment = Alignment.Center) {
            Text(text = "${weekDay.day.maxTemp} / ${weekDay.day.minTemp}\u00B0")
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            var icon by remember {
                mutableStateOf<Bitmap?>(null)
            }
            LaunchedEffect(key1 = true) {
                icon = vm.fetchImage(weekDay.day.condition.iconURL)
            }
            if (icon == null) {
                CircularProgressIndicator()
            } else {
                Image(
                    modifier = Modifier.size(50.dp, 50.dp),
                    bitmap = icon!!.asImageBitmap(),
                    contentDescription = ""
                )
            }
        }
    }

}