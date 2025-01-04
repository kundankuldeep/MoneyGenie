package com.jetbrains.moneygenie.screens.recipient

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.components.TransactionType
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import com.jetbrains.moneygenie.screens.addRecipients.AddRecipientScreen
import com.jetbrains.moneygenie.screens.transactions.AddTransactionScreen
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 15/12/24
 **/
class RecipientScreenModel : ScreenModel, KoinComponent {

    private val transactionRepository: TransactionRepository by inject()

    var recipient: Recipient? = null

    val totalLent = mutableStateOf(0.0)
    val totalBorrowed = mutableStateOf(0.0)

    var transactions = mutableStateOf(ArrayList<Transaction>())

    fun initViews(recipient: Recipient) {
        this.recipient = recipient
        getTransactionsData()
        calculateAccountTotal()
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

    private fun addDummyTransactions() {
        val transactions = createDummyTransactions()
        this.transactions.value = transactions
    }

    fun onEditRecipientClick(navigator: Navigator) {
        recipient?.let {
            navigator.push(AddRecipientScreen({}))
        }
    }

    fun onAddTransactionClick(onBack: (shouldRefresh: Boolean) -> Unit, navigator: Navigator) {
        recipient?.id?.let {
            navigator.push(AddTransactionScreen({ isRefresh ->
                if (isRefresh) {
                    refreshRecipientScreen()
                    onBack.invoke(true)
                }
            }, it, null))
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

    private fun createDummyTransactions(): ArrayList<Transaction> {
        val transactions = ArrayList<Transaction>()

        transactions.add(Transaction().apply {
            id = 1L
            recipientId = 101L
            amount = 500.0
            type = TransactionType.Lent.value
            note = "Salary"
            createdDate = Clock.System.now().toEpochMilliseconds()
            modifiedDate = Clock.System.now().toEpochMilliseconds()
        })

        transactions.add(Transaction().apply {
            id = 2L
            recipientId = 102L
            amount = 150.0
            type = TransactionType.Lent.value
            note = "Groceries"
            createdDate = Clock.System.now().toEpochMilliseconds()
            modifiedDate = Clock.System.now().toEpochMilliseconds()
        })

        transactions.add(Transaction().apply {
            id = 3L
            recipientId = 103L
            amount = 300.0
            type = TransactionType.Borrowed.value
            note = "Freelance Project"
            createdDate = Clock.System.now().toEpochMilliseconds()
            modifiedDate = Clock.System.now().toEpochMilliseconds()
        })

        transactions.add(Transaction().apply {
            id = 4L
            recipientId = 104L
            amount = 50.0
            type = TransactionType.Lent.value
            note = "Coffee"
            createdDate = Clock.System.now().toEpochMilliseconds()
            modifiedDate = Clock.System.now().toEpochMilliseconds()
        })

        transactions.add(Transaction().apply {
            id = 5L
            recipientId = 105L
            amount = 200.0
            type = TransactionType.Borrowed.value
            note = "Electricity Bill"
            createdDate = Clock.System.now().toEpochMilliseconds()
            modifiedDate = Clock.System.now().toEpochMilliseconds()
        })

        return transactions
    }

    fun onDeleteRecipientClick(navigator: Navigator) {
        // show a confirmation bottom sheet, says will delete all transactions to that user
        // and will delete that user from DB, and this action can't be undo.
        // on confirmation archive that user in DB mark as inactive
    }
}