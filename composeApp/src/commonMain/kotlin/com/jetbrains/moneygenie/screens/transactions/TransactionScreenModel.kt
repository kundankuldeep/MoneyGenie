package com.jetbrains.moneygenie.screens.transactions

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.components.TransactionFilterType
import com.jetbrains.moneygenie.components.TransactionType
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import com.jetbrains.moneygenie.screens.transactions.addTransactions.AddTransactionScreen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by Kundan on 12/01/25
 **/

class TransactionScreenModel : ScreenModel, KoinComponent {

    private var isInitialized = false
    private var navigator: Navigator? = null
    private var recipientId: Long? = null

    private val transactionRepository: TransactionRepository by inject()

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _selectedFilter = MutableStateFlow<TransactionFilterType?>(null)
    val selectedFilter = _selectedFilter.asStateFlow()

    var showDeleteTransactionDialog by mutableStateOf(false)

    var currentTransaction: Transaction? = null

    fun initViews(navigator: Navigator, recipientId: Long) {
        if (!isInitialized) {
            this.navigator = navigator
            this.recipientId = recipientId
            getAllTransactionsForRecipient()
            isInitialized = true
        }
    }

    fun updateFilter(filter: TransactionFilterType?) {
        _selectedFilter.value = filter
        applyFilter()
    }

    private fun getAllTransactionsForRecipient() {
        recipientId?.let { id ->
            screenModelScope.launch {
                try {
                    _isLoading.value = true
                    val transactionsList = transactionRepository.getTransactionsForRecipient(id)
                    _transactions.value = transactionsList
                } catch (e: Exception) {
                    _errorMessage.value = "Error fetching transactions: ${e.message}"
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    private fun applyFilter() {
        val recipientId = recipientId ?: return
        screenModelScope.launch {
            _isLoading.value = true
            try {
                val allTransactions = transactionRepository.getTransactionsForRecipient(recipientId)
                val filteredTransactions = when (_selectedFilter.value) {
                    TransactionFilterType.Older -> allTransactions.sortedByDescending { it.createdDate }
                    TransactionFilterType.Lent -> allTransactions.filter { it.type == TransactionType.Lent.value }
                    TransactionFilterType.Borrowed -> allTransactions.filter { it.type == TransactionType.Borrowed.value }
                    else -> allTransactions
                }
                _transactions.value = filteredTransactions
            } catch (e: Exception) {
                _errorMessage.value = "Error fetching transactions: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onEditTransactionClick(transaction: Transaction) {
        recipientId?.let { rId ->
            navigator?.push(AddTransactionScreen(rId, transaction) {})
        }
    }

    fun onDeleteTransactionClick() {
        // use current transaction here
    }
}
