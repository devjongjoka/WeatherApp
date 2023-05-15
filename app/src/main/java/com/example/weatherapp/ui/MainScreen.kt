package com.example.weatherapp.ui

import android.annotation.SuppressLint

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.*
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
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch { menuState.conceal() }
            nav.navigate(Routes.Manage.route)
        },
        content = { Icon(Icons.Default.Add, contentDescription = "search") }
    )
    LazyColumn {

        items(vm.locations.value) { savedLocation ->
            Card(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(
                        onClick = {
                            scope.launch {
                                vm._zip = savedLocation.zip
                                vm.searchWeather(vm._zip)
                                menuState.conceal()
                                nav.navigate(Routes.Location.route)
                            }
                        },
                        content = { Text(savedLocation.locationName) }
                    )
                    Button(
                        onClick = {
                            scope.launch {
                                vm.deleteLocation(savedLocation)
                                vm.locations.value=vm.getLocations()
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



