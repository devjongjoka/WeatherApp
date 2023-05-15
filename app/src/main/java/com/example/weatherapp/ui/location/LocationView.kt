package com.example.weatherapp.ui.location

import android.content.Intent
import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.ui.Components.DailyTile
import com.example.weatherapp.ui.Components.HourlyRow
import com.example.weatherapp.ui.WeatherViewModel
import android.net.Uri
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@ExperimentalFoundationApi
@Composable
fun LocationView(
    vm: WeatherViewModel,
    location: String
) {
    Box(
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll(ScrollState(0))
        ) {
            val context = LocalContext.current
            Text("View Current Location in Google Maps", style = TextStyle(textDecoration = TextDecoration.Underline) , modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("geo:$location")
                context.startActivity(intent)
            })
            if (vm.waiting.value) {
                CircularProgressIndicator()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = vm.current.value?.current!!.temp_f.toInt().toString() + "\u00B0",
                            fontSize = 70.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = vm.current.value?.current!!.condition.text,
                                fontSize = 25.sp,
                            )
                            Text(
                                text = "${vm.current.value?.forecast!!.forecastday.first().day.maxTemp} / ${vm.current.value?.forecast!!.forecastday.first().day.minTemp} \u00B0",
                                fontSize = 16.sp,
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            var icon by remember {
                                mutableStateOf<Bitmap?>(null)
                            }
                            LaunchedEffect(key1 = vm.current.value?.location!!.name) {
                                icon = vm.fetchImage(vm.current.value?.current!!.condition.iconURL)
                            }
                            if (icon == null) {
                                CircularProgressIndicator()
                            } else {
                                Image(
                                    modifier = Modifier.size(100.dp, 100.dp),
                                    bitmap = icon!!.asImageBitmap(),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                    Text(
                        text = "${vm.current.value?.location!!.name}, ${vm.current.value?.location!!.region}",
                        fontSize = 32.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                LazyColumn(
                                    contentPadding = PaddingValues(vertical = 8.dp),
                                    modifier = Modifier.height(335.dp)
                                ) {
                                    val filtered =
                                        vm.current.value?.forecast!!.forecastday.first().hour.filter {
                                            it.time.substring(
                                                11,
                                                13
                                            ).toInt() >= vm.current.value?.current!!.time.substring(
                                                11,
                                                13
                                            ).toInt()
                                        }
                                    val nextDayHours =
                                        vm.current.value?.forecast!!.forecastday[1].hour.subList(
                                            0,
                                            (24 - filtered.size)
                                        )
                                    val hours = filtered + nextDayHours
                                    items(hours) { hourlyWeather ->
                                        HourlyRow(hourlyWeather = hourlyWeather, vm)
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            AqiDial(vm.current.value?.current!!.air.index)
                            HumidityDial(vm.current.value?.current!!.humidity)
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .height(200.dp)
                                    .padding(8.dp),
                            ) {
                                DailyTile(vm.current.value?.forecast!!.forecastday[0], vm, "Today")
                                DailyTile(vm.current.value?.forecast!!.forecastday[1], vm, "Tomorrow")
                                DailyTile(
                                    vm.current.value?.forecast!!.forecastday[2],
                                    vm,
                                    vm.current.value?.forecast!!.forecastday[2].date.substring(5, 10)
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun AqiDial(
    idx: Int
) {
    val color: Color
    val msg: String
    when(idx) {
        1 -> {
            color = Color.Green
            msg = "Good"}
        2 -> {color = Color.Green
            msg = "Moderate"}
        3 -> {color = Color.Yellow
            msg = "Moderate"}
        4 -> {color = Color.Yellow
            msg = "Unhealthy"}
        5 -> {color = Color.Red
            msg = "Unhealthy"}
        6 -> {color = Color.Red
            msg = "Hazardous"}
        else -> {
            color = Color.Green
            msg = "Good"
        }
    }

    var done by remember { mutableStateOf(false) }
    val currVal = animateFloatAsState(
        targetValue = if(done) { (idx/6.0).toFloat() } else { 0f },
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) { done = true }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(80.dp * 2f),
        shape = RoundedCornerShape(8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp * 2f),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(80.dp * 2f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(60.dp * 2f)
                ) {
                    drawArc(
                        color = color,
                        startAngle = -90f,
                        sweepAngle = 360 * currVal.value,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text("AQI:(${(currVal.value * 6).toInt()})\n${msg}", textAlign = TextAlign.Center)
            }
        }
    }
}

@Composable
fun HumidityDial(
    percent: Int
) {
    var done by remember { mutableStateOf(false) }
    val currVal = animateFloatAsState(
        targetValue =
        if (done) {
            (percent/100.0).toFloat()
        } else { 0f },
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) { done = true }
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(80.dp * 2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .size(80.dp * 2f),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp * 2f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier.size(60.dp * 2f)
                ) {
                    drawArc(
                        color = Color.Blue,
                        startAngle = -90f,
                        sweepAngle = 360 * currVal.value,
                        useCenter = false,
                        style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    "Humidity:\n" + (currVal.value * 100).toInt().toString() + "%",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
