package com.ozgurbaykal.carcaretracker.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


@Composable
fun SettingsScreen (
    navController: NavController
){
    Column() {
        Text(text = "SettingsScreen")
        Text(text = "SettingsScreen")
    }
}