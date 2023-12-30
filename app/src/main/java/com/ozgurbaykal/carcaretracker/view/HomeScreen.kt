package com.ozgurbaykal.carcaretracker.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.model.Vehicles
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme
import com.ozgurbaykal.carcaretracker.viewmodel.VehiclesViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(navController: NavController, viewModel: VehiclesViewModel) {
    val vehiclesList by viewModel.vehiclesList.collectAsState()

    Column {
        Text(text = "HomeScreen", style = MaterialTheme.typography.bodyLarge)

        LazyColumn {
            items(vehiclesList) { vehicle ->
                VehiclesRow(vehicle)
            }
        }
    }
}
@Composable
fun VehiclesRow(vehicle: Vehicles) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Araç ikonunu göster
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Örnek ikon
            contentDescription = vehicle.vehicleName,
            modifier = Modifier.size(40.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Araç bilgilerini göster
        Column {
            Text(vehicle.vehicleName, fontWeight = FontWeight.Bold)
            Text("Sigorta Bitiş: ${vehicle.insuranceExpiryDate}")
            Text("Bakım Tarihi: ${vehicle.maintenanceDate}")
        }
    }
}

@Preview
@Composable
fun MyViewPreview() {
    CarCareTrackerTheme {

    }
}