package com.jetbrains.moneygenie.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Created by Kundan on 19/12/24
 **/

object PreferenceManager {

    var dataStore: DataStore<Preferences>? = null

    // Save a preference
    suspend fun <T> savePreference(key: Preferences.Key<T>, value: T) {
        dataStore?.edit { preferences ->
            preferences[key] = value
        }
    }

    // Retrieve a preference
    suspend fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): T {
        return dataStore?.data?.map { preferences ->
            preferences[key] ?: defaultValue
        }?.first() ?: defaultValue
    }

    // Clear all preferences
    suspend fun clearPreferences() {
        dataStore?.edit { preferences ->
            preferences.clear()
        }
    }
}
