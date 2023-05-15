package com.example.weatherapp.ui.nav

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.location.LocationView
import com.example.weatherapp.ui.manage.ManageView

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun NavGraph(
    location: String,
    nav: NavHostController
) {
    val vm: WeatherViewModel = viewModel()
    NavHost(
        navController = nav,
        startDestination = Routes.Location.route
    ) {
        composable(Routes.Location.route) {
            LocationView(vm, location)
        }
        composable(Routes.Manage.route) {
            ManageView(vm, nav)
        }
    }
}