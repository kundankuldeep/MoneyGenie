package com.jetbrains.moneygenie.screens.recipient.editRecipients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.components.Genders
import com.jetbrains.moneygenie.components.getGenderFromValue
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository
import kotlinx.coroutines.launch
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
    var recipientGender by mutableStateOf<Genders?>(null)
    var recipientNote by mutableStateOf("")

    private var currentRecipient: Recipient? = null

    fun updateName(name: String) {
        recipientName = name
    }

    fun updateNumber(phone: String) {
        recipientNumber = phone
    }

    fun updateEmail(email: String) {
        recipientEmail = email
    }

    fun updateGender(gender: Genders?) {
        recipientGender = gender
    }

    fun updateNote(note: String) {
        recipientNote = note
    }

    private fun updateCurrentRecipient() {
        currentRecipient?.let { recipient ->
            recipientRepository.updateRecipient(recipient) {
                name = recipientName
                phone = recipientNumber
                email = recipientEmail
                gender = recipientGender?.value ?: Genders.MALE.value
                note = recipientNote
            }
        }
    }

    fun onUpdateClick(navigator: Navigator, onBack: (updatedRecipient: Recipient?) -> Unit) {
        if (validateFields()) {
            screenModelScope.launch {
                updateCurrentRecipient()
                navigateToDashboard(navigator, onBack = onBack)
            }
        }
    }

    private fun navigateToDashboard(
        navigator: Navigator,
        onBack: (updatedRecipient: Recipient?) -> Unit
    ) {
        onBack.invoke(Recipient().apply {
            id = currentRecipient?.id ?: 0
            name = recipientName
            phone = recipientNumber
            email = recipientEmail
            gender = recipientGender?.value ?: Genders.MALE.value
            note = recipientNote
        })
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

            recipientGender == null -> {
                println("Please select recipient gender")
                false
            }

            else -> true
        }
    }

    fun initViews(recipient: Recipient) {
        this.currentRecipient = recipient
        updateName(recipient.name)
        updateEmail(recipient.email)
        updateNumber(recipient.phone)
        updateGender(getGenderFromValue(recipient.gender))
        updateNote(recipient.note ?: "")
    }
}