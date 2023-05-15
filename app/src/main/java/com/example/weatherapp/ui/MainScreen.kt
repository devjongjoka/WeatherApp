package com.example.weatherapp.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.ClipData.Item
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.*
import com.example.weatherapp.models.savedLocation
import com.example.weatherapp.ui.Components.DailyTile
import com.example.weatherapp.ui.Components.HourlyRow
import com.example.weatherapp.ui.location.LocationView
import com.example.weatherapp.ui.nav.NavGraph
import com.example.weatherapp.ui.nav.Routes
import kotlinx.coroutines.launch
import java.util.*

@ExperimentalMaterialApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(
    location: String
) {
    val vm: WeatherViewModel = viewModel()
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
        backLayerContent = { BackContent(nav, menuState, vm) },
        scaffoldState = menuState,
        gesturesEnabled = menuState.isRevealed,
        backLayerBackgroundColor = MaterialTheme.colors.background,
        frontLayerShape = RectangleShape
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BackContent(
    nav: NavHostController,
    menuState: BackdropScaffoldState,
    vm: WeatherViewModel
) {
    val backStackEntry = nav.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()
    val currentDestination = backStackEntry.value?.destination

    val locations = remember{ mutableStateOf(listOf<savedLocation>())}

    LaunchedEffect(key1 = menuState) {
        locations.value = vm.getLocations()
        Log.d("8 == D", locations.value.toString())
    }

    Button(
        onClick = {
            scope.launch { menuState.conceal() }
            nav.navigate(Routes.Manage.route)
        },
        content = { Icon(Icons.Default.Add, contentDescription = "search") }
    )
    LazyColumn {

        items(locations.value) { savedLocation ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = {
                            scope.launch {
                                vm._zip = savedLocation.zip
                                menuState.conceal()
                                nav.navigate(Routes.Location.route)
                            }
                        },
                        content = { Text(savedLocation.locationName) }
                    )
                    Button(
                        onClick = {
                            scope.launch {
                                vm._repository.deleteLocation(savedLocation)
                                menuState.conceal()
                            }
                        },
                        content = { Text("Delete Location") }
                    )
                }

            }

        }
    }
}



