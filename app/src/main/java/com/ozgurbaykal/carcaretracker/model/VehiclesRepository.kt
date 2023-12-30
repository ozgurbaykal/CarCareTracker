package com.ozgurbaykal.carcaretracker.model

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VehiclesRepository @Inject constructor(
    private val vehicleDao: VehicleDao
) {
     fun getAllVehicles(): Flow<List<Vehicles>> {
        return vehicleDao.getAllVehicles()
    }



    suspend fun insertVehicle(vehicle: Vehicles) {
        vehicleDao.insertVehicle(vehicle)
    }

    suspend fun deleteVehicle(vehicle: Vehicles) {
        vehicleDao.deleteVehicle(vehicle)
    }
}
