package com.example.weatherapp.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.*
import com.example.weatherapp.ui.Components.DailyTile
import com.example.weatherapp.ui.Components.HourlyRow
import com.example.weatherapp.ui.location.LocationView
import com.example.weatherapp.ui.nav.NavGraph
import com.example.weatherapp.ui.nav.Routes
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    location: String
) {
    val vm = remember{ WeatherViewModel() }
    val menuState = rememberBackdropScaffoldState(BackdropValue.Concealed)
    val scope = rememberCoroutineScope()
    val nav = rememberNavController()

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
        frontLayerContent = { NavGraph(location, nav) },
        backLayerContent = { BackContent(nav) },
        scaffoldState = menuState,
        gesturesEnabled = menuState.isRevealed,
        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerShape = RectangleShape
    )
}

@Composable
fun BackContent(
    nav: NavHostController
) {
    val backStackEntry = nav.currentBackStackEntryAsState()
    val currentDestination = backStackEntry.value?.destination
    Row() {
        Button(
            onClick = {
                nav.navigate(Routes.Manage.route)
            },
            content = { Icon(Icons.Default.Add, contentDescription = "search") }
        )
    }
}