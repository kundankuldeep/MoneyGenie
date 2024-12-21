package com.jetbrains.moneygenie.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created by Kundan on 20/12/24
 **/
object PreferenceKeys {
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    val COUNTER = intPreferencesKey("counter")

    val APP_THEME = stringPreferencesKey("app_theme") // Light, Dark, System
    val LANGUAGE = stringPreferencesKey("language") // e.g., "en", "es"
    val PASSCODE = stringPreferencesKey("passcode")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}