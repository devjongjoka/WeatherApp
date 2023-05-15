package com.example.weatherapp.ui.manage

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.weatherapp.Networking.WeatherFetcher
import com.example.weatherapp.models.savedLocation
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.nav.Routes
import kotlinx.coroutines.launch
import java.util.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalFoundationApi
@Composable
fun ManageView(
    vm: WeatherViewModel,
    nav: NavController,
    input: MutableState<String> = remember { mutableStateOf("")}
) {
    var showDialog = remember { mutableStateOf(false) }
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val locations = vm.locations.value

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text(text = "Are you sure you want to save this location?") },
            confirmButton = {
                Button(onClick = {
                    showDialog.value = false
                    if (input.value.matches("\\d{5}".toRegex())) {
                        vm.searchWeather(input.value)
                        scope.launch { vm.addLocation(savedLocation(id = UUID.randomUUID(),
                            zip = input.value,
                            locationName = getName(input.value)
                                )
                            )
                        }
                        nav.navigate(Routes.Location.route)
                    } else {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Please enter a valid ZIP code.")
                        }
                    }
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog.value = false
                }) {
                    Text("Deny")
                }
            }
        )
    }

    Scaffold(scaffoldState = scaffoldState) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = input.value,
                onValueChange = { new: String -> input.value = new },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search By ZIP") },
                trailingIcon = {
                    IconButton(onClick = {
                        showDialog.value = true
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "search")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

suspend fun getName(zipCode: String) : String{
    val fetcher = WeatherFetcher()
    val result = fetcher.getWeather(zipCode)
    return "${result.location.name}, ${result.location.region}"
}


