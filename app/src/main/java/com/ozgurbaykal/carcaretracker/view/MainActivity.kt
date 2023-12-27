package com.ozgurbaykal.carcaretracker.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCareTrackerTheme {

            }


        }
        val intent = Intent(this, WelcomePage::class.java)
        startActivity(intent)

    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarCareTrackerTheme {

    }
    WelcomePage()
}