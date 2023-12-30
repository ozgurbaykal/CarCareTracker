package com.ozgurbaykal.carcaretracker

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CarCareTrackerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}