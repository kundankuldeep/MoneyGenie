package com.jetbrains.moneygenie.screens.addRecipient

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 06/10/24
 **/
class AddRecipientScreenModel : ScreenModel, KoinComponent {

    // Inject repositories using Koin
    private val recipientRepository: RecipientRepository by inject()
    private val transactionRepository: TransactionRepository by inject()


    // State variables for storing user inputs
    var name = mutableStateOf("")
    var contact = mutableStateOf("")
    var email = mutableStateOf("")
    var gender = mutableStateOf("")

    // State for error checking
    var nameError = mutableStateOf(false)
    var contactError = mutableStateOf(false)

    fun saveRecipient(name: String, contact: String, email: String, gender: String) {
        addSampleData()
    }

    private fun addSampleData() {
        screenModelScope.launch {
            // Add a recipient
            val newRecipient = Recipient().apply {
                name = "John Doe"
                phone = "123-456-7890"
                email = "123-456-7890"
                gender = "123-456-7890"
                note = "123-456-7890"
            }
            recipientRepository.addRecipient(newRecipient)
            println("size is - ${recipientRepository.getAllRecipients().size}")
        }
    }
}