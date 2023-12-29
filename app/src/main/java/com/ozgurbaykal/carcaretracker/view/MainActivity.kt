package com.ozgurbaykal.carcaretracker.view

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ozgurbaykal.carcaretracker.R
import com.ozgurbaykal.carcaretracker.ui.theme.LightGray
import com.ozgurbaykal.carcaretracker.ui.theme.MainBlue
import com.ozgurbaykal.carcaretracker.ui.theme.MainGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCareTrackerTheme {
                val navController = rememberNavController()

                MyBottomNavigation(navController)
            }


        }


        val dataStoreManager = DataStoreManager(applicationContext)

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
fun MyBottomNavigation(navController: NavHostController) {
    Scaffold(
        modifier = Modifier,
        floatingActionButton = {
            Box() {
                FloatingActionButton(
                    onClick = { /* stub */ },
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
                enterTransition = {
                    EnterTransition.None
                },
                exitTransition = {
                    ExitTransition.None
                },
                popExitTransition = {
                    ExitTransition.None
                },
                popEnterTransition = {
                    EnterTransition.None
                }
            ) {
                composable("home") { HomeScreen(navController) }
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
/* BottomAppBar(
                 containerColor = Color.White,
                 contentColor = MainBlue,
                 modifier = Modifier.shadow(
                     elevation = 30.dp,
                     ambientColor = Color.Black,
                     spotColor = Color.Black
                 ),
             ) {
                 Column(
                     Modifier
                         .weight(1f)
                         .fillMaxHeight()
                         .fillMaxWidth()
                         .clickable(onClick = {
                             //navController.navigate("home")
                         }),
                     horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center,
                 ) {
                     Icon(Icons.Filled.Home, contentDescription = "Home", modifier = Modifier.padding(end = 40.dp))
                 }
                 Column(
                     Modifier
                         .weight(1f)
                         .fillMaxHeight()
                         .fillMaxWidth()
                         .clickable(onClick = {
                             //navController.navigate("settings")
                         }),
                     horizontalAlignment = Alignment.CenterHorizontally,
                     verticalArrangement = Arrangement.Center,
                 ) {
                     Icon(Icons.Filled.Settings, contentDescription = "Settings", modifier = Modifier.padding(start = 40.dp))
                 }


             }*/

/*@Composable
fun aa(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(Icons.Filled.Favorite, contentDescription = null) },
                        label = { Text(stringResource(screen.resourceId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->

    }
}
*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarCareTrackerTheme {

        val navController = rememberNavController()

        MyBottomNavigation(navController)
    }

}