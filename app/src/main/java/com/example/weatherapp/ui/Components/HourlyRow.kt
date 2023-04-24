package com.example.weatherapp.ui.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.models.Hour

@Composable
fun HourlyRow(hourlyWeather: Hour) {
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
            Text(text = hourlyWeather.time)
            Text(text = "${hourlyWeather.temperature}°F")
            Icon(
                painter = painterResource(id = R.drawable.outline_wb_sunny_24),
                contentDescription = "cloud"
            )
        }
    }
}