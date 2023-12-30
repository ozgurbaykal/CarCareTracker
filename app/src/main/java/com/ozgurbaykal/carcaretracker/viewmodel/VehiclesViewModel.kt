package com.ozgurbaykal.carcaretracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozgurbaykal.carcaretracker.model.Vehicles
import com.ozgurbaykal.carcaretracker.model.VehiclesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehiclesViewModel @Inject constructor(
    private val repository: VehiclesRepository
) : ViewModel() {
    private val _vehiclesList = MutableStateFlow<List<Vehicles>>(emptyList())
    val vehiclesList: StateFlow<List<Vehicles>> = _vehiclesList

    init {
        loadVehicles()
    }

    private fun loadVehicles() {
        viewModelScope.launch {
            repository.getAllVehicles().collect { vehicles ->
                _vehiclesList.value = vehicles
            }
        }
    }

     fun insertVehicle(vehicles: Vehicles){
        viewModelScope.launch {
            repository.insertVehicle(vehicles)
        }
    }
}
