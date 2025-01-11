package com.jetbrains.moneygenie.screens.recipient.addRecipients

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 06/10/24
 **/
class AddRecipientScreenModel : ScreenModel, KoinComponent {

    // Inject repositories using Koin
    private val recipientRepository: RecipientRepository by inject()
    private val transactionRepository: TransactionRepository by inject()

    var recipientName by mutableStateOf("")
    var recipientNumber by mutableStateOf("")
    var recipientEmail by mutableStateOf("")
    private var recipientGender by mutableStateOf("")
    var recipientNote by mutableStateOf("")
    var outstandingBalance by mutableStateOf("")
    private var outstandingBalanceOwedBy by mutableStateOf("")
    var outstandingBalanceNote by mutableStateOf("")

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

    fun updateOutstandingBalance(balance: String) {
        outstandingBalance = balance
    }

    fun updateOutstandingBalanceOwedBy(balanceOwedBy: String) {
        outstandingBalanceOwedBy = balanceOwedBy
    }

    fun updateOutstandingBalanceNote(balanceNote: String) {
        outstandingBalanceNote = balanceNote
    }

    private fun addCurrentRecipient() {
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

        // if outstanding balance is there, add a transaction
        if (outstandingBalance.isNotEmpty()) {
            outstandingBalance.toDoubleOrNull()?.let { amt ->
                val newTransaction = Transaction().apply {
                    recipientId = userId
                    amount = amt
                    type = outstandingBalanceOwedBy
                    note = outstandingBalanceNote
                }
                transactionRepository.addTransaction(newTransaction)
            }
        }
    }

    fun onSaveClick(navigator: Navigator, onBack: (shouldRefresh: Boolean) -> Unit) {
        if (validateFields()) {
            screenModelScope.launch {
                addCurrentRecipient()
                navigateToDashboard(navigator, onBack = onBack, true)
            }
        }
    }

    fun navigateToDashboard(
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

            outstandingBalance.isNotEmpty() && outstandingBalanceOwedBy.isEmpty() -> {
                println("Outstanding Balance is there but, Owed By is not mentioned")
                false
            }

            outstandingBalance.isEmpty() && outstandingBalanceOwedBy.isNotEmpty() -> {
                println("Outstanding Balance is not there but, Owed By is mentioned")
                false
            }

            else -> true
        }
    }
}