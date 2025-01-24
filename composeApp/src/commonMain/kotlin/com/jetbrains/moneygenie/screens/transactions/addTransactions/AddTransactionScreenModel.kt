package com.jetbrains.moneygenie.screens.transactions.addTransactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.components.TransactionType
import com.jetbrains.moneygenie.components.getTransactionFromValue
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import com.jetbrains.moneygenie.expects.showMessage
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 15/12/24
 **/
class AddTransactionScreenModel : ScreenModel, KoinComponent {
    private val transactionRepository: TransactionRepository by inject()

    var transactionAmount by mutableStateOf("")
    var transactionType by mutableStateOf<TransactionType?>(null)
    var transactionNote by mutableStateOf("")

    private var recipientId: Long? = null
    private var existingTransaction: Transaction? = null

    fun initialize(recipientId: Long, transaction: Transaction?) {
        this.recipientId = recipientId
        this.existingTransaction = transaction

        // If editing an existing transaction, prefill fields
        transaction?.let {
            transactionAmount = it.amount.toString()
            transactionType = getTransactionFromValue(it.type)
            transactionNote = it.note.orEmpty()
        }
    }

    fun updateTransactionAmount(amount: String) {
        transactionAmount = amount
    }

    fun updateTransactionType(type: TransactionType) {
        transactionType = type
    }

    fun updateTransactionNote(note: String) {
        transactionNote = note
    }

    fun onSaveClick(navigator: Navigator, onBack: (shouldRefresh: Boolean) -> Unit) {
        if (validateFields()) {
            screenModelScope.launch {
                saveTransaction()
                onBack.invoke(true)
                navigator.pop()
            }
        }
    }

    private fun saveTransaction() {
        recipientId?.let { rId ->
            transactionAmount.toDoubleOrNull()?.let { amt ->
                if (existingTransaction == null) {
                    transactionRepository.addTransaction(Transaction().apply {
                        recipientId = rId
                        amount = amt
                        type = transactionType?.value
                        note = transactionNote
                    }) // Add new
                } else {
                    transactionRepository.updateTransaction(existingTransaction!!) {
                        amount = amt
                        type = transactionType?.value
                        note = transactionNote
                    } // Update existing
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        return when {
            transactionAmount.isEmpty() -> {
                showMessage("Transaction amount is empty.")
                false
            }

            transactionType == null -> {
                showMessage("Please select a transaction type.")
                false
            }

            else -> true
        }
    }
}