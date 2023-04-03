package com.example.weatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable fun MainScreen() {

    val weekDayList: List<WeekDay> =
        (0..6).map { i ->
            WeekDay(
                "Monday",
                60 + i,
                40 + i,
                "Rain"
            )
        }

    val hourlyWeatherList: List<HourlyWeather> =
        (0..20).map { i -> HourlyWeather(
            "${i+1}:00",
            60+i )
        }

    Scaffold(
        topBar = { MyAppBar() }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "New York, NY",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(8.dp)
                )
                LocalIcon()
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Partly cloudy with a chance of rain in the afternoon",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }
            Text(
                text = "69" + "\u00B0",
                fontSize = 64.sp,
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth().height(400.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(vertical = 8.dp),
                        modifier = Modifier.height(350.dp)
                    ) {
                        items(hourlyWeatherList) { hourlyWeather ->
                            HourlyWeatherItem(hourlyWeather = hourlyWeather)
                        }
                    }
                }
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Partly cloudy with a chance of rain in the afternoon",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                LazyRow {
                    items(weekDayList) { weekDay ->
                        WeekDayItem(weekDay = weekDay)
                    }
                }
            }
        }
    }
}

@Composable
fun LocalIcon(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(horizontal = 74.dp)
            .wrapContentSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            Icons.Filled.LocationOn,
            contentDescription = "Sun Icon",
            //tint = Color.Yellow,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Composable
fun HourlyWeatherItem(hourlyWeather: HourlyWeather) {
    Card(
        modifier = Modifier.padding(4.dp).width(170.dp),
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

data class WeekDay(
    val day: String,
    val high: Int,
    val low: Int,
    val condition: String
)
@Composable
fun WeekDayItem(weekDay: WeekDay) {
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


@Composable
fun MyAppBar() {
    TopAppBar(
        title = { Text(text = "WeatherApp") },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation icon click */ }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}

data class HourlyWeather(
    val time: String,
    val temperature: Int,
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        MainScreen()
    }
}