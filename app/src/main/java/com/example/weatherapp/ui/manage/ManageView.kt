package com.example.weatherapp.ui.manage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.weatherapp.ui.WeatherViewModel
import com.example.weatherapp.ui.nav.Routes


@ExperimentalFoundationApi
@Composable
fun ManageView(
    vm: WeatherViewModel,
    nav: NavController,
    input: MutableState<String> = remember { mutableStateOf("")}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = input.value,
            onValueChange = {new: String -> input.value = new},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search By ZIP") },
            trailingIcon = {
                IconButton(onClick = {
                    vm.searchWeather(input.value)
                    nav.navigate(Routes.Location.route)
                }) {
                    Icon(Icons.Default.Search, contentDescription = "search")
                }
            }
        )
    }
}
