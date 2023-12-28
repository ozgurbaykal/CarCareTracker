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
import androidx.lifecycle.lifecycleScope
import com.ozgurbaykal.carcaretracker.ui.theme.CarCareTrackerTheme
import com.ozgurbaykal.carcaretracker.util.DataStoreManager
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarCareTrackerTheme {

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
                        dataStoreManager.saveBoolean(DataStoreManager.PreferencesKeys.FIRST_LAUNCH, false)
                    }
                }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarCareTrackerTheme {

    }
    WelcomePage()
}