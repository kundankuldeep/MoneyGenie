package com.jetbrains.moneygenie.screens.profile

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.screens.profile.editProfile.EditProfileScreen
import com.jetbrains.moneygenie.screens.settings.SettingsScreen
import com.jetbrains.moneygenie.screens.settings.changePasscode.ChangePasscodeScreen
import com.jetbrains.moneygenie.screens.settings.changeSecurityQuestion.ChangeSecurityQuestionScreen
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

/**
 * Created by Kundan on 15/12/24
 **/
class ProfileScreenModel : ScreenModel, KoinComponent {

    var name = mutableStateOf("")
    var email = mutableStateOf("")
    var phone = mutableStateOf("")
    var dob = mutableStateOf("")
    var gender = mutableStateOf("")

    fun initViews() {
        getAllProfileData()
    }

    private fun getAllProfileData() {
        screenModelScope.launch {
            name.value = PreferenceManager.getPreference(PreferenceKeys.USERNAME) ?: ""
            email.value = PreferenceManager.getPreference(PreferenceKeys.EMAIL) ?: ""
            phone.value = PreferenceManager.getPreference(PreferenceKeys.PHONE) ?: ""
            dob.value = PreferenceManager.getPreference(PreferenceKeys.DOB) ?: ""
            gender.value = PreferenceManager.getPreference(PreferenceKeys.GENDER) ?: ""
        }
    }

    fun deleteAccountClick(navigator: Navigator) {
        println("deleteAccountClick")
    }

    fun clearAllDataClick(navigator: Navigator) {
        println("clearAllDataClick")
    }

    fun onExportClick(navigator: Navigator) {
        println("onExportClick")
    }

    fun onImportClick(navigator: Navigator) {
        println("onImportClick")
    }

    fun onSettingClick(navigator: Navigator) {
        navigator.push(SettingsScreen())
    }

    fun onChangeSecurityQuestionClick(navigator: Navigator) {
        navigator.push(ChangeSecurityQuestionScreen())
    }

    fun onChangePasswordClick(navigator: Navigator) {
        navigator.push(ChangePasscodeScreen())
    }

    fun onEditProfileClick(navigator: Navigator) {
        navigator.push(EditProfileScreen { isRefresh ->
            if (isRefresh) screenModelScope.launch { getAllProfileData() }
        })
    }
}