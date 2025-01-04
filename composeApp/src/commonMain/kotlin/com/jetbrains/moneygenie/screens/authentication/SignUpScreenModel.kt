package com.jetbrains.moneygenie.screens.authentication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.models.SelfData
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.screens.home.HomeScreen
import com.jetbrains.moneygenie.utils.JsonParser
import com.jetbrains.moneygenie.utils.ValidationUtils.isValidEmail
import kotlinx.coroutines.launch

/**
 * Created by Kundan on 28/11/24
 **/
class SignUpScreenModel : ScreenModel {

    // Fields for the signup form
    var fullName by mutableStateOf("")

    var email by mutableStateOf("")

    var phone by mutableStateOf("")

    var dob by mutableStateOf("")

    private var gender by mutableStateOf("")

    var passcode by mutableStateOf("")

    var confirmPasscode by mutableStateOf("")

    var securityQuestion by mutableStateOf("")

    var securityAnswer by mutableStateOf("")

    // Setters
    fun updateFullName(value: String) {
        fullName = value
    }

    fun updateEmail(value: String) {
        email = value
    }

    fun updatePhone(value: String) {
        phone = value
    }

    fun updateDob(value: String) {
        dob = value
    }

    fun updateGender(value: String) {
        gender = value
    }

    fun updatePasscode(value: String) {
        passcode = value
    }

    fun updateConfirmPasscode(value: String) {
        confirmPasscode = value
    }

    fun updateSecurityAnswer(value: String) {
        securityAnswer = value
    }

    // Validation
    private fun validateFields(): Boolean {
        return when {
            fullName.isBlank() -> {
                println("Full Name cannot be empty")
                false
            }

            email.isBlank() || !isValidEmail(email) -> {
                println("Enter a valid email address")
                false
            }

            phone.isBlank() || phone.length != 10 || !phone.all { it.isDigit() } -> {
                println("Enter a valid 10-digit phone number")
                false
            }

            dob.isBlank() -> {
                println("Date of Birth cannot be empty")
                false
            }

            gender.isBlank() -> {
                println("Please select a gender")
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

            securityQuestion.isBlank() -> {
                println("Please select a security question.")
                false
            }

            securityAnswer.isBlank() -> {
                println("Please enter the answer to the selected security question.")
                false
            }

            else -> true
        }
    }

    fun onSignUpClick(navigator: Navigator) {
        if (validateFields()) {
            val selfData = SelfData(
                name = fullName,
                email = email,
                phone = phone,
                dob = dob,
                gender = gender
            )

            val dataStr = JsonParser.toJson(selfData)

            // save logged in to preferences
            screenModelScope.launch {
                PreferenceManager.savePreference(PreferenceKeys.IS_ACCOUNT_CREATED, true)
                PreferenceManager.savePreference(PreferenceKeys.SELF_DATA, dataStr)
                PreferenceManager.savePreference(PreferenceKeys.PASSCODE, passcode)
                PreferenceManager.savePreference(
                    PreferenceKeys.SECURITY_QUESTION,
                    securityQuestion
                )
                PreferenceManager.savePreference(PreferenceKeys.SECURITY_ANSWER, securityAnswer)
            }

            navigator.push(HomeScreen)
        }
    }

    fun getQuestions(): List<String> {
        return listOf(
            "What are the last four digits of your Driving License?",
            "What is the brand of your first Car?",
            "What is the year of date of birth?",
            "What are the last four digits of your PAN Card?",
            "What are the last four digits of your Aadhaar Card?"
        )
    }

}