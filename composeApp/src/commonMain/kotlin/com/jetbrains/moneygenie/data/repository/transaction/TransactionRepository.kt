package com.jetbrains.moneygenie.data.repository.transaction

import com.jetbrains.moneygenie.data.models.Transaction

/**
 * Created by Kundan on 23/10/24
 **/
interface TransactionRepository {
    fun addTransaction(transaction: Transaction)
    fun getTransactionById(id: Long): Transaction?
    fun getAllTransactions(): List<Transaction>
    fun getTransactionsForRecipient(recipientId: Long): List<Transaction>
    fun updateTransaction(transaction: Transaction, update: Transaction.() -> Unit)
    fun deleteTransactionById(transaction: Transaction)
    fun getAllLentAmount(): Double
    fun getAllLentAmountFor(recipientId: Long): Double
    fun getAllBorrowedAmount(): Double
    fun getAllBorrowedAmountFor(recipientId: Long): Double
    fun getLatestTransactionByRecipient(): List<Transaction>
}