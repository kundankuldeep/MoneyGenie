package com.jetbrains.moneygenie.screens.profile.editProfile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.components.Genders
import com.jetbrains.moneygenie.components.getGenderFromValue
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.utils.ValidationUtils
import kotlinx.coroutines.launch

/**
 * Created by Kundan on 15/12/24
 **/
class EditProfileScreenModel : ScreenModel {

    // Fields for the Edit Profile Form
    var fullName by mutableStateOf("")

    var email by mutableStateOf("")

    var phone by mutableStateOf("")

    var dob by mutableStateOf("")

    var gender by mutableStateOf<Genders?>(null)

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

    fun updateGender(value: Genders) {
        gender = value
    }

    private fun validateFields(): Boolean {
        return when {
            fullName.isBlank() -> {
                println("Full Name cannot be empty")
                false
            }

            email.isBlank() || !ValidationUtils.isValidEmail(email) -> {
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

            gender == null -> {
                println("Please select a gender")
                false
            }

            else -> true
        }
    }


    fun onUpdateProfileClick(navigator: Navigator, onBackClick: (Boolean) -> Unit) {
        if (validateFields()) {
            screenModelScope.launch {
                PreferenceManager.savePreference(PreferenceKeys.USERNAME, fullName)
                PreferenceManager.savePreference(PreferenceKeys.EMAIL, email)
                PreferenceManager.savePreference(PreferenceKeys.PHONE, phone)
                PreferenceManager.savePreference(PreferenceKeys.DOB, dob)
                PreferenceManager.savePreference(
                    PreferenceKeys.GENDER,
                    gender?.value ?: Genders.MALE.value
                )
            }

            onBackClick.invoke(true)
            navigator.pop()
        }
    }

    fun initViews() {
        initInitialValues()
    }

    private fun initInitialValues() {
        screenModelScope.launch {
            screenModelScope.launch {
                fullName = PreferenceManager.getPreference(PreferenceKeys.USERNAME) ?: ""
                email = PreferenceManager.getPreference(PreferenceKeys.EMAIL) ?: ""
                phone = PreferenceManager.getPreference(PreferenceKeys.PHONE) ?: ""
                dob = PreferenceManager.getPreference(PreferenceKeys.DOB) ?: ""
                gender = getGenderFromValue(PreferenceManager.getPreference(PreferenceKeys.GENDER))
            }
        }
    }
}