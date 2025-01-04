package com.jetbrains.moneygenie.screens.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.screens.home.HomeScreen
import kotlinx.coroutines.launch

/**
 * Created by Kundan on 01/01/25
 **/
class LoginScreenModel : ScreenModel {

    var passcode by mutableStateOf("")

    fun updatePasscode(value: String) {
        passcode = value
    }

    fun onLoginClick(navigator: Navigator) {
        // get the passcode from user data
        screenModelScope.launch {
            val savedPasscode = PreferenceManager.getPreference(PreferenceKeys.PASSCODE, null)
            if (passcode == savedPasscode) {
                navigator.push(HomeScreen)
            } else {
                // Show error message
            }
        }
    }
}