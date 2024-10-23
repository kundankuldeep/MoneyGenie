package com.jetbrains.moneygenie.data.repository.transaction

import com.jetbrains.moneygenie.data.models.Transaction

/**
 * Created by Kundan on 23/10/24
 **/
interface TransactionRepository {
    fun addTransaction(transaction: Transaction)
    fun getTransactionById(id: String): Transaction?
    fun getAllTransactions(): List<Transaction>
    fun getTransactionsForRecipient(recipientId: String): List<Transaction>
    fun updateTransaction(transaction: Transaction, update: Transaction.() -> Unit)
    fun deleteTransactionById(id: String)
}