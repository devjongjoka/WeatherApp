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

@Composable
fun DailyTile(weekDay: Daily) {
    Card{
        Column(
            modifier = Modifier
                .padding(4.dp)
                .size(120.dp),
        ) {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = weekDay.day, fontSize = 16.sp)
            }
            Row() {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Hi: ${weekDay.high}")
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Lo: ${weekDay.low}")
                }
            }
            Row(horizontalArrangement = Arrangement.Center) {
                Text(text = weekDay.condition, fontSize = 16.sp)
            }
        }
    }
}