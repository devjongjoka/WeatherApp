package com.example.weatherapp.data

import androidx.room.*
import com.example.weatherapp.models.savedLocation
import java.util.*

@Dao
interface LocationsDao {

    @Query("select * from locations")
    suspend fun getLocations(): List<savedLocation>

    @Insert
    suspend fun addLocation(savedLocation: savedLocation)

    @Delete
    suspend fun deleteLocation(savedLocation: savedLocation)

}

class UUIDConverter{

    @TypeConverter
    fun stringToUUID(uuid: String): UUID {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun uuidToString(uuid: UUID): String {
        return uuid.toString()
    }
}

@Database(entities = [savedLocation::class], version = 2, exportSchema = false)
@TypeConverters(UUIDConverter::class)
abstract class LocationsDatabase : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao
}