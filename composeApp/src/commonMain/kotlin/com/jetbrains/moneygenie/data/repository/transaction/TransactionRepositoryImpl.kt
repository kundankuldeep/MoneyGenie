package com.jetbrains.moneygenie.data.repository.transaction

import com.jetbrains.moneygenie.components.TransactionType
import com.jetbrains.moneygenie.data.local.RealmManager
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository

/**
 * Created by Kundan on 23/10/24
 **/
class TransactionRepositoryImpl(
    private val realmManager: RealmManager,
    private val recipientRepository: RecipientRepository
) : TransactionRepository {

    override fun addTransaction(transaction: Transaction) {
        realmManager.addObject(transaction)
        updateRecipientAggregates(transaction.recipientId)
    }

    override fun getTransactionById(id: Long): Transaction? {
        return realmManager.getObjectById(Transaction::class, id)
    }

    override fun getAllTransactions(): List<Transaction> {
        return realmManager.getAllObjects(Transaction::class)
    }

    override fun getTransactionsForRecipient(recipientId: Long): List<Transaction> {
        val allTransactions = getAllTransactions()
        return allTransactions.filter { it.recipientId == recipientId }
    }

    override fun updateTransaction(transaction: Transaction, update: Transaction.() -> Unit) {
        realmManager.updateObject(transaction, update)
        updateRecipientAggregates(transaction.recipientId)
    }

    override fun deleteTransactionById(transaction: Transaction) {
        realmManager.deleteObjectById(Transaction::class, transaction.id.toString())
        updateRecipientAggregates(transaction.recipientId)
    }

    override fun getAllLentAmount(): Double {
        return realmManager.getAllObjects(Transaction::class)
            .filter { it.type == TransactionType.Lent.value }
            .sumOf { it.amount }
    }

    override fun getAllLentAmountFor(recipientId: Long): Double {
        return realmManager.getAllObjects(Transaction::class)
            .filter { it.type == TransactionType.Lent.value && it.recipientId == recipientId }
            .sumOf { it.amount }
    }

    override fun getAllBorrowedAmount(): Double {
        return realmManager.getAllObjects(Transaction::class)
            .filter { it.type == TransactionType.Borrowed.value }
            .sumOf { it.amount }
    }

    override fun getAllBorrowedAmountFor(recipientId: Long): Double {
        return realmManager.getAllObjects(Transaction::class)
            .filter { it.type == TransactionType.Borrowed.value && it.recipientId == recipientId }
            .sumOf { it.amount }
    }

    override fun getLatestTransactionByRecipient(): List<Transaction> {
        return realmManager.getAllObjects(Transaction::class)
            .groupBy { it.recipientId }
            .mapNotNull { (_, transactions) ->
                transactions.maxByOrNull { it.createdDate } // Get the latest transaction per recipient
            }
    }

    private fun updateRecipientAggregates(recipientId: Long) {
        val recipient = recipientRepository.getRecipientById(recipientId)

        if (recipient != null) {
            recipientRepository.updateRecipient(recipient) {
                this.totalLent = getAllLentAmountFor(recipientId)
                this.totalBorrowed = getAllBorrowedAmountFor(recipientId)
            }
        }
    }
}
