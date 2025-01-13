package com.jetbrains.moneygenie.screens.settings.changePasscode

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import kotlinx.coroutines.launch

/**
 * Created by Kundan on 12/01/25
 **/
class ChangePasscodeScreenModel : ScreenModel {

    private lateinit var navigator: Navigator
    var passcode by mutableStateOf("")

    var confirmPasscode by mutableStateOf("")

    var securityQuestion by mutableStateOf("")

    var securityAnswer by mutableStateOf("")

    private var savedAnswer: String? = null

    fun updatePasscode(value: String) {
        passcode = value
    }

    fun updateConfirmPasscode(value: String) {
        confirmPasscode = value
    }

    fun updateSecurityAnswer(value: String) {
        securityAnswer = value
    }

    fun initViews(navigator: Navigator) {
        this.navigator = navigator
        screenModelScope.launch {
            securityQuestion =
                PreferenceManager.getPreference(PreferenceKeys.SECURITY_QUESTION) ?: ""
            savedAnswer = PreferenceManager.getPreference(PreferenceKeys.SECURITY_ANSWER) ?: ""
        }
    }

    fun onUpdatePasscodeClick() {
        if (validateFields()) {
            // update the passcode
            screenModelScope.launch {
                PreferenceManager.savePreference(PreferenceKeys.PASSCODE, passcode)
            }
            // Pop current screen
            navigator.pop()
        }
    }

    private fun validateFields(): Boolean {
        return when {
            securityAnswer.isBlank() -> {
                println("Security answer cannot be empty")
                false
            }

            securityAnswer != savedAnswer -> {
                println("Security answer is incorrect")
                false
            }

            passcode.isBlank() || passcode.length < 6 -> {
                println("Passcode must be at least 6 characters")
                false
            }

            confirmPasscode.isBlank() || confirmPasscode != passcode -> {
                println("Passcodes do not match")
                false
            }

            else -> true
        }
    }
}