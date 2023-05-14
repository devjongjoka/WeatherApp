package com.example.weatherapp

import android.Manifest
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.PermissionChecker
import com.example.weatherapp.ui.MainScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.*

class MainActivity : ComponentActivity() {

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationClient: FusedLocationProviderClient
    private val locationString = mutableStateOf("")

    @ExperimentalMaterialApi
    @ExperimentalComposeUiApi
    @ExperimentalFoundationApi
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationClient = LocationServices.getFusedLocationProviderClient(this)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                locationString.value = "${result.lastLocation?.latitude},${result.lastLocation?.longitude}"
            }
        }
        setContent {
            WeatherAppTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(location = locationString.value)
                    LocationPermissions()
                }
            }
        }
    }

    @ExperimentalPermissionsApi
    @Composable
    fun LocationPermissions() {
        val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
        when(permissionState.status) {
            PermissionStatus.Granted -> {
                requestLocation()
            }
            is PermissionStatus.Denied -> {
                LaunchedEffect(key1 = true) {
                    permissionState.launchPermissionRequest()
                }
            }
        }
    }

    fun requestLocation() {
        val locReq = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10 * 1000
        ).build()
        if(PermissionChecker
                .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PermissionChecker.PERMISSION_GRANTED) {
            locationClient.requestLocationUpdates(
                locReq,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        MainScreen("")
    }
}