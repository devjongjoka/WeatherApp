package com.example.weatherapp.data.impl

import android.app.Application
import androidx.room.Room
import com.example.weatherapp.models.savedLocation
import com.example.weatherapp.data.ILocationsRepository
import com.example.weatherapp.data.LocationsDatabase

class LocationsDatabaseRepository(app: Application) : ILocationsRepository {

    private val db: LocationsDatabase

    init {
        db = Room.databaseBuilder(
            app,
            LocationsDatabase::class.java,
            "locations.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    override suspend fun getLocations(): List<savedLocation> {
        return db.locationsDao().getLocations()
    }

    override suspend fun deleteLocation(savedLocation: savedLocation) {
        db.locationsDao().deleteLocation(savedLocation)
    }

    override suspend fun addLocation(savedLocation: savedLocation) {
        db.locationsDao().addLocation(savedLocation)
    }

}