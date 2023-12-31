package com.ozgurbaykal.carcaretracker.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.model.Vehicles
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme
import com.ozgurbaykal.carcaretracker.ui.theme.LightGray
import com.ozgurbaykal.carcaretracker.ui.theme.MainBlue
import com.ozgurbaykal.carcaretracker.ui.theme.MainGray
import com.ozgurbaykal.carcaretracker.viewmodel.VehiclesViewModel
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(navController: NavController, viewModel: VehiclesViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    val vehiclesList by viewModel.vehiclesList.collectAsState()
    val filteredVehiclesList = vehiclesList.filter {
        it.vehicleName.contains(searchQuery, ignoreCase = true)
    }

    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 22.dp)
                .background(Color.White),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White, // Card background color
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(all = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                CustomSearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChanged = { searchQuery = it },
                )
                IconButton(onClick = {  }) {
                    Icon(
                        painter = painterResource(id = R.drawable.order),
                        contentDescription = "Sıralama",
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = 9.dp),
                        tint = MainBlue
                    )
                }
            }
        }

        LazyColumn {
            items(filteredVehiclesList) { vehicle ->
                VehiclesRow(vehicle)
            }
            item {
                Text(
                    text = stringResource(id = R.string.end),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun CustomSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
) {

    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChanged(it) },
        label = { Text("Ara") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search, // Arama ikonu
                contentDescription = "Arama",
                tint = MainBlue
            )
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChanged("") }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Temizle",
                        tint = MainBlue
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MainBlue,
            disabledTextColor = Color.Transparent,
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = MainBlue,
            unfocusedIndicatorColor = MainBlue,
            disabledIndicatorColor = Color.Transparent,
            cursorColor = MainBlue
        ),
        singleLine = true,
        modifier = Modifier
            .padding(start = 8.dp)
    )
}

@Composable
fun VehiclesRow(vehicle: Vehicles) {
    val context = LocalContext.current
    val iconId = context.resources.getIdentifier(
        vehicle.iconResourceName,
        "drawable",
        context.packageName
    )

    val colorString = vehicle.iconColor
    val color = Color(android.graphics.Color.parseColor(colorString))

    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    // Date objelerini formatla
    val formattedInsuranceExpiryDate = dateFormat.format(vehicle.insuranceExpiryDate)
    val formattedMaintenanceDate = dateFormat.format(vehicle.maintenanceDate)



    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 12.dp)
            .background(Color.White),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (iconId != 0) {


                Icon(
                    painter = painterResource(id = iconId), // Örnek ikon
                    contentDescription = vehicle.iconResourceName,
                    modifier = Modifier.size(70.dp, 70.dp),
                    tint = color
                )
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.question),
                    contentDescription = "İkon bulunamadı",
                    modifier = Modifier.size(70.dp, 70.dp),
                )
            }


            Spacer(modifier = Modifier.width(8.dp))

            // Araç bilgilerini göster
            Column {
                Text(vehicle.vehicleName, fontWeight = FontWeight.Bold, color = MainBlue)
                val maxWidth = 160.dp // Metinler için maksimum genişlik

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.width(maxWidth)) {
                        Text(stringResource(R.string.insurance_expiry) + " " + formattedInsuranceExpiryDate, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.width(maxWidth)) {
                        Text(stringResource(R.string.maintenance_date) + " " + formattedMaintenanceDate, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color.Green)
                    )
                }

            }
            // Durum göstergesi için Spacer ve Icon ekleyebilirsiniz
        }
    }
}


@Preview
@Composable
fun MyViewPreview() {
    Column {
        Text(text = "HomeScreen", style = MaterialTheme.typography.bodyLarge)



        LazyColumn {
            item {
                Text(
                    text = stringResource(id = R.string.end),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.question),
            contentDescription = "Sıralama",
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp) // Kenarlardan boşluk eklemek için padding.

        )
    }
}