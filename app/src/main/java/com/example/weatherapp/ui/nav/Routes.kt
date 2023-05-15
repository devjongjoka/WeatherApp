package com.example.weatherapp.ui.nav

sealed class Routes(val route: String) {
    object Location : Routes("location")
    object Manage : Routes("manage")
}