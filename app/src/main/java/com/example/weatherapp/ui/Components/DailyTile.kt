package com.example.weatherapp.ui.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.models.Daily
import com.example.weatherapp.models.Day
import com.example.weatherapp.models.ForecastDay

@Composable
fun DailyTile(weekDay: ForecastDay) {
    Card{
        Column(
            modifier = Modifier
                .padding(4.dp)
                .size(120.dp),
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = weekDay.date, fontSize = 16.sp)
            }
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Hi: ${weekDay.day.maxTemp}")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Lo: ${weekDay.day.minTemp}")
                }
            }
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = weekDay.day.condition.text, fontSize = 16.sp)
            }
        }
    }
}