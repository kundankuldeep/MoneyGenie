package com.jetbrains.moneygenie.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

/**
 * Created by Kundan on 20/12/24
 **/
object PreferenceKeys {
    val IS_ACCOUNT_CREATED = booleanPreferencesKey("is_account_created")
    val USERNAME = stringPreferencesKey("username")
    val EMAIL = stringPreferencesKey("email")
    val PHONE = stringPreferencesKey("phone")
    val DOB = stringPreferencesKey("dob")
    val GENDER = stringPreferencesKey("gender")
    val PASSCODE = stringPreferencesKey("passcode")
    val SECURITY_QUESTION = stringPreferencesKey("securityQuestion")
    val SECURITY_ANSWER = stringPreferencesKey("securityAnswer")

    val APP_THEME = stringPreferencesKey("app_theme") // Light, Dark, System
    val LANGUAGE = stringPreferencesKey("language") // e.g., "en", "es"
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}