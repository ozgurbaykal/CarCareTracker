package com.ozgurbaykal.carcaretracker.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Vehicles(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val vehicleName: String,
    val insuranceExpiryDate: Date,
    val maintenanceDate: Date,
    val iconResourceName: String,
    val iconColor: String = "#FFFFFF"
    )
