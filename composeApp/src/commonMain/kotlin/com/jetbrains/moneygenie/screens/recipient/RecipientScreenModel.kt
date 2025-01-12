package com.jetbrains.moneygenie.screens.recipient

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
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

    private val transactionRepository: TransactionRepository by inject()

    var recipient by mutableStateOf<Recipient?>(null)
    var navigator: Navigator? = null

    val totalLent = mutableStateOf(0.0)
    val totalBorrowed = mutableStateOf(0.0)

    var transactions = mutableStateOf(ArrayList<Transaction>())

    fun initViews(navigator: Navigator, recipient: Recipient) {
        if (!isInitialized) {
            // Initialize state only once
            this.recipient = recipient
            this.navigator = navigator
            getTransactionsData()
            calculateAccountTotal()
            isInitialized = true
        }

    }

    private fun getTransactionsData() {
        transactions.value.clear()
        screenModelScope.launch {
            recipient?.id?.let {
                transactions.value.addAll(transactionRepository.getTransactionsForRecipient(it))
            }
        }
    }

    private fun calculateAccountTotal() {
        screenModelScope.launch {
            recipient?.id?.let {
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

    fun onAddTransactionClick(onBack: (shouldRefresh: Boolean) -> Unit) {
        recipient?.id?.let {
            navigator?.push(AddTransactionScreen(it, null) { isRefresh ->
                if (isRefresh) {
                    refreshRecipientScreen()
                    onBack.invoke(true)
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

    }

    fun onRemindThemClick() {

    }

    fun onDeleteRecipientClick() {
        // show a confirmation bottom sheet, says will delete all transactions to that user
        // and will delete that user from DB, and this action can't be undo.
        // on confirmation archive that user in DB mark as inactive
    }

    fun onEditTransactionClick(transaction: Transaction) {
        recipient?.id?.let { rId ->
            navigator?.push(AddTransactionScreen(rId, transaction) {})
        }

    }

    fun onDeleteTransactionClick(transaction: Transaction) {

    }

    fun onViewAllTransactionsClick() {
        recipient?.id?.let { rId ->
            navigator?.push(TransactionScreen(recipientId = rId))
        }

    }
}