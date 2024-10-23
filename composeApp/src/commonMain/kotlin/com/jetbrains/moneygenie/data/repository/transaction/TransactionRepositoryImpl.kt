package com.jetbrains.moneygenie.data.repository.transaction

import com.jetbrains.moneygenie.data.local.RealmManager
import com.jetbrains.moneygenie.data.models.Transaction

/**
 * Created by Kundan on 23/10/24
 **/
class TransactionRepositoryImpl(private val realmManager: RealmManager) : TransactionRepository {

    override fun addTransaction(transaction: Transaction) {
        realmManager.addObject(transaction)
    }

    override fun getTransactionById(id: String): Transaction? {
        return realmManager.getObjectById(Transaction::class, id)
    }

    override fun getAllTransactions(): List<Transaction> {
        return realmManager.getAllObjects(Transaction::class)
    }

    override fun getTransactionsForRecipient(recipientId: String): List<Transaction> {
        val allTransactions = getAllTransactions()
        return allTransactions.filter { it.recipientId == recipientId }
    }

    override fun updateTransaction(transaction: Transaction, update: Transaction.() -> Unit) {
        realmManager.updateObject(transaction, update)
    }

    override fun deleteTransactionById(id: String) {
        realmManager.deleteObjectById(Transaction::class, id)
    }
}
