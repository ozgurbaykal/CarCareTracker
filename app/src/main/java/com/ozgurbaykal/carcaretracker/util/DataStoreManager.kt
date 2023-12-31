package com.ozgurbaykal.carcaretracker.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "car_tracker_data_store")
        @Volatile
        private var INSTANCE: DataStoreManager? = null

        fun getInstance(context: Context): DataStoreManager {
            return INSTANCE ?: synchronized(this) {
                val newDataStore = context.dataStore
                val newDataStoreManager = DataStoreManager(newDataStore)
                INSTANCE = newDataStoreManager
                newDataStoreManager
            }
        }
    }

    enum class PreferencesKeys {
        FIRST_LAUNCH,
    }

    suspend fun saveBoolean(key: PreferencesKeys, value: Boolean) {
        val dataStoreKey = booleanPreferencesKey(key.name)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    fun readBoolean(key: PreferencesKeys, defaultValue: Boolean): Flow<Boolean> {
        val dataStoreKey = booleanPreferencesKey(key.name)
        return dataStore.data
            .map { preferences ->
                preferences[dataStoreKey] ?: defaultValue
            }
    }

    suspend fun saveString(key: PreferencesKeys, value: String) {
        val dataStoreKey = stringPreferencesKey(key.name)
        dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    fun readString(key: PreferencesKeys, defaultValue: String): Flow<String> {
        val dataStoreKey = stringPreferencesKey(key.name)
        return dataStore.data
            .map { preferences ->
                preferences[dataStoreKey] ?: defaultValue
            }
    }
}
