package com.ozgurbaykal.carcaretracker.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = "car_tracker_data_store")

    enum class PreferencesKeys {
        FIRST_LAUNCH,
    }

    suspend fun saveBoolean(key: PreferencesKeys, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key.name)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    fun readBoolean(key: PreferencesKeys, defaultValue: Boolean): Flow<Boolean> {
        val dataStoreKey = booleanPreferencesKey(key.name)
        return context.dataStore.data
            .map { preferences ->
                preferences[dataStoreKey] ?: defaultValue
            }
    }


    suspend fun saveString(key: PreferencesKeys, value: String) {
        val dataStoreKey = stringPreferencesKey(key.name)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    fun readString(key: PreferencesKeys, defaultValue: String): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key.name)
        return context.dataStore.data
            .map { preferences ->
                preferences[dataStoreKey] ?: defaultValue
            }
    }
}
