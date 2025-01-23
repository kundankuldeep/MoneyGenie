package com.jetbrains.moneygenie.screens.recipient

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
import com.jetbrains.moneygenie.expects.openMessageApp
import com.jetbrains.moneygenie.screens.recipient.editRecipients.EditRecipientsScreen
import com.jetbrains.moneygenie.screens.transactions.TransactionScreen
import com.jetbrains.moneygenie.screens.transactions.addTransactions.AddTransactionScreen
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 15/12/24
 **/
class RecipientScreenModel : ScreenModel, KoinComponent {
    private var isInitialized = false

    var showSettleAccountDialog by mutableStateOf(false)
    var showDeleteAccountDialog by mutableStateOf(false)
    var showDeleteTransactionDialog by mutableStateOf(false)

    var currentTransaction: Transaction? = null

    private val recipientRepository: RecipientRepository by inject()
    private val transactionRepository: TransactionRepository by inject()

    var recipientId: Long? = null
    var recipient by mutableStateOf<Recipient?>(null)
    var navigator: Navigator? = null

    val totalLent = mutableStateOf(0.0)
    val totalBorrowed = mutableStateOf(0.0)

    var transactions = mutableStateOf(ArrayList<Transaction>())

    fun initViews(navigator: Navigator, recipient: Long) {
        if (!isInitialized) {
            // Initialize state only once
            this.recipientId = recipient
            this.navigator = navigator
            getRecipientData()
            getTransactionsData()
            calculateAccountTotal()
            isInitialized = true
        }

    }

    private fun getRecipientData() {
        screenModelScope.launch {
            recipientId?.let {
                recipient = recipientRepository.getRecipientById(it)
            }
        }
    }

    private fun getTransactionsData() {
        transactions.value.clear()
        screenModelScope.launch {
            recipientId?.let {
                transactions.value.addAll(transactionRepository.getTransactionsForRecipient(it))
            }
        }
    }

    private fun calculateAccountTotal() {
        screenModelScope.launch {
            recipientId?.let {
                totalLent.value = transactionRepository.getAllLentAmountFor(it)
                totalBorrowed.value = transactionRepository.getAllBorrowedAmountFor(it)
            }
        }
    }

    fun onEditRecipientClick() {
        recipient?.let {
            navigator?.push(EditRecipientsScreen(it) { updatedRecipient ->
                updatedRecipient?.let { data ->
                    recipient = data
                }
            })
        }
    }

    fun onAddTransactionClick() {
        recipientId?.let {
            navigator?.push(AddTransactionScreen(it, null) { isRefresh ->
                if (isRefresh) {
                    refreshRecipientScreen()
                }
            })
        }
    }

    private fun refreshRecipientScreen() {
        screenModelScope.launch {
            getTransactionsData()
            calculateAccountTotal()
        }
    }

    fun onSettleAccountClick() {
        recipientId?.let { recipientId ->
            screenModelScope.launch {
                val balance = totalLent.value - totalBorrowed.value
                if (balance != 0.0) {
//                    transactionRepository.addTransaction(recipientId, balance)
                    refreshRecipientScreen()
                }
            }
        }
    }

    fun onRemindThemClick() {
        recipient?.let {
            val balance = totalLent.value - totalBorrowed.value
            val message = if (balance > 0) {
                "Hey ${it.name}, you owe me ₹$balance. Please settle the amount soon."
            } else {
                "Hey ${it.name}, I owe you ₹${-balance}. I will settle it soon."
            }
            openMessageApp(message)
        }
    }

    fun onDeleteRecipientClick() {
        // show a confirmation bottom sheet, says will delete all transactions to that user
        // and will delete that user from DB, and this action can't be undo.
        // on confirmation archive that user in DB mark as inactive
    }

    fun onEditTransactionClick(transaction: Transaction) {
        recipientId?.let { rId ->
            navigator?.push(AddTransactionScreen(rId, transaction) {})
        }

    }

    fun onDeleteTransactionClick() {
        // use current transaction here
    }

    fun onViewAllTransactionsClick() {
        recipientId?.let { rId ->
            navigator?.push(TransactionScreen(recipientId = rId))
        }
    }
}