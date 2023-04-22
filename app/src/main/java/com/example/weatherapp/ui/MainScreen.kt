package com.example.weatherapp.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.*
import com.example.weatherapp.ui.Components.DailyTile
import com.example.weatherapp.ui.Components.HourlyRow
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScreen() {
    val vm = WeatherViewModel()
    val menuState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val scope = rememberCoroutineScope()

    BackdropScaffold(
        appBar = {
            TopAppBar(
            title = { Text(text = "WeatherApp") },
            navigationIcon = {
                if (menuState.isConcealed) {
                    IconButton(onClick = { scope.launch { menuState.reveal() } }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Location menu") }
                } else {
                    IconButton(onClick = { scope.launch { menuState.conceal() } }) {
                        Icon(Icons.Default.Close, contentDescription = "Close") }
                }
            }
        ) },
        frontLayerContent = { FrontContent(vm) },
        backLayerContent = { BackContent(vm) },
        scaffoldState = menuState,
        gesturesEnabled = menuState.isRevealed,
        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerShape = RectangleShape
    )
}

@Composable
fun BackContent(
    vm: WeatherViewModel,
    input: MutableState<String> = remember { mutableStateOf("") }
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        OutlinedTextField(
            value = input.value,
            onValueChange = {new: String -> input.value = new},
            modifier = Modifier.fillMaxWidth(),
            placeholder = {Text("Search By ZIP")},
            trailingIcon = {Icon(Icons.Default.Search, contentDescription = "search")}
        )
    }

}

@Composable
fun FrontContent(
    vm: WeatherViewModel
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
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
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
                    items(vm.hourlyWeatherList) { hourlyWeather ->
                        HourlyRow(hourlyWeather = hourlyWeather)
                    }
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AqiDial(25)
                Dial(80)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow {
                items(vm.weekDayList) { dayWeather ->
                    DailyTile(weekDay = dayWeather)
                }
            }
        }
    }
}


@Composable
fun AqiDial(
    idx: Int
) {
    var done by remember { mutableStateOf(false) }
    val currVal = animateFloatAsState(
        targetValue = if(done) { (idx/300.0).toFloat() } else { 0f },
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        )
    )
    LaunchedEffect(key1 = true) { done = true }
    Box(
        modifier = Modifier.size(60.dp * 2f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(60.dp * 2f)
        ) {
            drawArc(
                color = Color.Green,
                startAngle = -90f,
                sweepAngle = 360 * currVal.value,
                useCenter = false,
                style = Stroke(width = 8.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Text("AQI:\nGood (${(currVal.value*300).toInt()})", textAlign = TextAlign.Center)
    }
}

@Composable
fun Dial(
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
    Box(
        modifier = Modifier.size(60.dp * 2f),
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
        Text("Humidity:\n"+(currVal.value*100).toInt().toString()+ "%", textAlign = TextAlign.Center)
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