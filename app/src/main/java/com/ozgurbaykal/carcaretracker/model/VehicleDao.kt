package com.ozgurbaykal.carcaretracker.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface VehicleDao {
    @Query("SELECT * FROM vehicles")
     fun getAllVehicles(): Flow<List<Vehicles>>

    @Insert
    suspend fun insertVehicle(vehicle: Vehicles)

    @Update
    suspend fun updateVehicle(vehicle: Vehicles)

    @Delete
    suspend fun deleteVehicle(vehicle: Vehicles)
}
