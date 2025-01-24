package com.jetbrains.moneygenie.screens.settings.changeSecurityQuestion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.expects.showMessage
import kotlinx.coroutines.launch

/**
 * Created by Kundan on 12/01/25
 **/
class ChangeSecurityQuestionScreenModel : ScreenModel {

    lateinit var navigator: Navigator
    var passcode by mutableStateOf("")
    var securityAnswer by mutableStateOf("")
    var securityQuestion by mutableStateOf("")
    private var yourPasscode: String? = null

    fun updatePasscode(value: String) {
        passcode = value
    }

    fun updateSecurityAnswer(value: String) {
        securityAnswer = value
    }

    fun updateSecurityQuestion(value: String) {
        securityQuestion = value
    }

    fun initViews(navigator: Navigator) {
        this.navigator = navigator
        screenModelScope.launch {
            securityQuestion =
                PreferenceManager.getPreference(PreferenceKeys.SECURITY_QUESTION, null) ?: ""
            yourPasscode = PreferenceManager.getPreference(PreferenceKeys.PASSCODE, null)
        }
    }

    fun onUpdateSecurityQuestionClicked() {
        if (validateFields()) {
            saveSecurityQuestion()
            navigator.pop()
        }
    }

    private fun saveSecurityQuestion() {
        screenModelScope.launch {
            PreferenceManager.savePreference(PreferenceKeys.SECURITY_QUESTION, securityQuestion)
            PreferenceManager.savePreference(PreferenceKeys.SECURITY_ANSWER, securityAnswer)
        }
    }

    private fun validateFields(): Boolean {
        return when {
            passcode.isBlank() || passcode.length < 6 || passcode != yourPasscode -> {
                showMessage("Passcode is not valid")
                false
            }

            securityAnswer.isBlank() -> {
                showMessage("Security answer cannot be empty")
                false
            }

            securityAnswer.length < 4 -> {
                showMessage("Security answer cannot be less than 4 characters")
                false
            }

            else -> true
        }
    }
}