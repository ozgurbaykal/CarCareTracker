package com.ozgurbaykal.carcaretracker.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme
import com.ozgurbaykal.carcaretracker.util.DataStoreManager
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.model.AppDatabase
import com.ozgurbaykal.carcaretracker.model.Vehicles
import com.ozgurbaykal.carcaretracker.model.VehiclesRepository
import com.ozgurbaykal.carcaretracker.ui.theme.LightGray
import com.ozgurbaykal.carcaretracker.ui.theme.MainBlue
import com.ozgurbaykal.carcaretracker.viewmodel.VehiclesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: VehiclesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCareTrackerTheme {
                val navController = rememberNavController()

                MyBottomNavigation(navController, viewModel)
            }
        }


        val dataStoreManager = DataStoreManager.getInstance(applicationContext)

        //IF IS NEW USER SHOW WELCOMEPAGE
        lifecycleScope.launch {
            dataStoreManager.readBoolean(DataStoreManager.PreferencesKeys.FIRST_LAUNCH, true)
                .collect { isFirstLaunch ->
                    if (isFirstLaunch) {
                        val intent = Intent(this@MainActivity, WelcomePage::class.java)
                        startActivity(intent)

                        //SAVE FIRST_LAUNCH TO FALSE
                        dataStoreManager.saveBoolean(
                            DataStoreManager.PreferencesKeys.FIRST_LAUNCH,
                            false
                        )
                    }
                }
        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomNavigation(navController: NavHostController, viewModel: VehiclesViewModel) {

    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            Box() {
                FloatingActionButton(
                    onClick = {

                        CoroutineScope(Dispatchers.IO).launch {

                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            val dateString = "29/12/2024"

                            val specificDate: Date = dateFormat.parse(dateString) ?: Date()


                            val newVehicle = Vehicles(
                                vehicleName = "My Car",
                                insuranceExpiryDate = specificDate, // Milisaniye cinsinden zaman damgası
                                maintenanceDate = specificDate, // Başka bir tarih için de aynı işlemi yapabilirsiniz
                                iconResourceName = "icon_res_name"
                            )
                            viewModel.insertVehicle(newVehicle)

                        }


                    },
                    containerColor = MainBlue,
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(80.dp)
                        .offset(y = 50.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = CircleShape,
                        ),
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        content = { innerPadding ->
            NavHost(
                navController,
                startDestination = "home",
                Modifier.padding(innerPadding),
            ) {

                composable("home") { HomeScreen(navController,viewModel) }
                composable("settings") { SettingsScreen(navController) }
            }
        },
        bottomBar = {

            BottomNavigation(
                backgroundColor = Color.White,
                contentColor = MainBlue
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItem(
                    icon = {
                        val isSelected = currentDestination?.hierarchy?.any { it.route == "home" } == true
                        Icon(Icons.Filled.Home, contentDescription = null, tint = if (isSelected) MainBlue else LightGray)
                    },

                    label = {
                        val isSelected = currentDestination?.hierarchy?.any { it.route == "home" } == true
                        Text(stringResource(R.string.home), color = if (isSelected) MainBlue else LightGray)
                    },

                    selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                    selectedContentColor = MainBlue,

                    onClick = {
                        navController.navigate("home") {

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true

                        }
                    }

                )

                BottomNavigationItem(

                    icon = {
                        val isSelected = currentDestination?.hierarchy?.any { it.route == "settings" } == true
                        Icon(Icons.Filled.Settings, contentDescription = null, tint = if (isSelected) MainBlue else LightGray)
                    },

                    label = {
                        val isSelected = currentDestination?.hierarchy?.any { it.route == "settings" } == true
                        Text(stringResource(R.string.settings), color = if (isSelected) MainBlue else LightGray)
                    },

                    selected = currentDestination?.hierarchy?.any { it.route == "settings" } == true,
                    selectedContentColor = MainBlue,
                    unselectedContentColor = LightGray,

                    onClick = {
                        navController.navigate("settings") {

                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true

                        }
                    }
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarCareTrackerTheme {

        val navController = rememberNavController()

        //MyBottomNavigation(navController)
    }

}