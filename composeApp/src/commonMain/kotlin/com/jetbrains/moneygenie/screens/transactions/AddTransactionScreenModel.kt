package com.jetbrains.moneygenie.screens.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 15/12/24
 **/
class AddTransactionScreenModel : ScreenModel, KoinComponent {
    private val transactionRepository: TransactionRepository by inject()

    var transactionBalance by mutableStateOf("")
    private var transactionBalanceOwedBy by mutableStateOf("")
    var transactionBalanceNote by mutableStateOf("")

    private var recipientId: Long? = null
    private var transactionData: Transaction? = null

    fun initialize(recipientId: Long, data: Transaction?) {
        this.recipientId = recipientId
        this.transactionData = data
    }

    fun updateTransactionBalance(balance: String) {
        transactionBalance = balance
    }

    fun updateTransactionBalanceOwedBy(balanceOwedBy: String) {
        transactionBalanceOwedBy = balanceOwedBy
    }

    fun updateTransactionBalanceNote(balanceNote: String) {
        transactionBalanceNote = balanceNote
    }


    fun onSaveClick(navigator: Navigator, onBack: (shouldRefresh: Boolean) -> Unit) {
        if (validateFields()) {
            screenModelScope.launch {
                saveCurrentTransaction()
                onBack.invoke(true)
                navigator.pop()
            }
        }
    }

    private fun saveCurrentTransaction() {
        recipientId?.let { rId ->
            transactionBalance.toDoubleOrNull()?.let { amt ->
                transactionRepository.addTransaction(
                    Transaction().apply {
                        recipientId = rId
                        amount = amt
                        type = transactionBalanceOwedBy
                        note = transactionBalanceNote
                    }
                )
            }
        }
    }

    private fun validateFields(): Boolean {
        return when {
            transactionBalance.isEmpty() -> {
                println("Transaction balance is empty.")
                false
            }

            transactionBalanceOwedBy.isEmpty() -> {
                println("Please select transaction type")
                false
            }

            else -> true
        }
    }
}