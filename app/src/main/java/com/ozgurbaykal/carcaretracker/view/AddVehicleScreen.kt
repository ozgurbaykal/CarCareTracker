package com.ozgurbaykal.carcaretracker.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme
import com.ozgurbaykal.carcaretracker.ui.theme.LightGray
import com.ozgurbaykal.carcaretracker.ui.theme.MainBlue
import com.ozgurbaykal.carcaretracker.ui.theme.MainGray
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddVehicleScreen: ComponentActivity(){
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent(){
            CarCareTrackerTheme {


            Scaffold(

                topBar = {
                    TopAppBar(
                        title = { Text(stringResource(id = R.string.add_vehicle), fontWeight = FontWeight.Bold) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MainGray,
                            titleContentColor = MainBlue,
                        ),
                        navigationIcon = {
                            IconButton(onClick = { finish() }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(id = R.string.back), tint = MainBlue)
                            }
                        },
                    )
                },
            ) { innerPadding ->
                Text(text = "AddVehicleScreen", modifier = Modifier.padding(innerPadding))

            }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        CarCareTrackerTheme {


            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Araç Ekle") },
                        navigationIcon = {
                            IconButton(onClick = { }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Geri Dön")
                            }
                        }
                    )
                },
            ) { innerPadding ->
                Text(text = "AddVehicleScreen", modifier = Modifier.padding(innerPadding))

            }        }

    }
}
