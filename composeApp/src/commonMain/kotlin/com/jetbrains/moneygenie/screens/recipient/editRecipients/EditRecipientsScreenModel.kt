package com.jetbrains.moneygenie.screens.recipient.editRecipients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 10/01/25
 **/
class EditRecipientsScreenModel : ScreenModel, KoinComponent {

    // Inject repositories using Koin
    private val recipientRepository: RecipientRepository by inject()

    var recipientName by mutableStateOf("")
    var recipientNumber by mutableStateOf("")
    var recipientEmail by mutableStateOf("")
    private var recipientGender by mutableStateOf("")
    var recipientNote by mutableStateOf("")

    fun updateName(name: String) {
        recipientName = name
    }

    fun updateNumber(phone: String) {
        recipientNumber = phone
    }

    fun updateEmail(email: String) {
        recipientEmail = email
    }

    fun updateGender(gender: String) {
        recipientGender = gender
    }

    fun updateNote(note: String) {
        recipientNote = note
    }

    private fun updateCurrentRecipient() {
        val userId = Clock.System.now().toEpochMilliseconds()

        // Add a recipient
        val newRecipient = Recipient().apply {
            id = userId
            name = recipientName
            phone = recipientNumber
            email = recipientEmail
            gender = recipientGender
            note = recipientNote
        }
        recipientRepository.addRecipient(newRecipient)
    }

    fun onUpdateClick(navigator: Navigator, onBack: (shouldRefresh: Boolean) -> Unit) {
        if (validateFields()) {
            screenModelScope.launch {
                updateCurrentRecipient()
                navigateToDashboard(navigator, onBack = onBack, true)
            }
        }
    }

    private fun navigateToDashboard(
        navigator: Navigator,
        onBack: (shouldRefresh: Boolean) -> Unit,
        shouldRefresh: Boolean = false
    ) {
        onBack.invoke(shouldRefresh)
        navigator.pop()
    }

    private fun validateFields(): Boolean {
        return when {
            recipientName.isBlank() -> {
                println("Recipient Name cannot be empty")
                false
            }

            recipientNumber.isBlank() || recipientNumber.length != 10 || !recipientNumber.all { it.isDigit() } -> {
                println("Enter a valid 10-digit phone number")
                false
            }

            recipientGender.isBlank() -> {
                println("Please select recipient gender")
                false
            }

            else -> true
        }
    }

    fun initViews(recipient: Recipient) {

    }
}