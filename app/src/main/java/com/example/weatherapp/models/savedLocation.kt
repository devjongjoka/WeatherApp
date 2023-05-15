package com.example.weatherapp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.*

@Entity(tableName = "locations")
data class savedLocation (
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @ColumnInfo
    val zip: String,
    @ColumnInfo
    val locationName: String,
){

}