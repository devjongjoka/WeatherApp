package data

import com.example.weatherapp.models.savedLocation

interface ILocationsRepository {

    suspend fun getLocations(): List<savedLocation>

    suspend fun deleteLocation(savedLocation: savedLocation)

    suspend fun addLocation(savedLocation: savedLocation)
}