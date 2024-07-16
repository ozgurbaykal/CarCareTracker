package com.ozgurbaykal.carcaretracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Vehicles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleName: String,
    val vehicleBrand: String,
    val vehicleBrandModel: String,
    val vehiclePlate: String,
    val odometerValue: Double,
    val odometerType: Odometers,
    val insuranceExpiryDate: Date,
    val maintenanceDate: Date,
    val iconResourceName: String,
    val iconColor: String = "#000000",
    val creationDate: Date,
    val note: String? = null,
    )


enum class Odometers{
    KM,
    MIL
}