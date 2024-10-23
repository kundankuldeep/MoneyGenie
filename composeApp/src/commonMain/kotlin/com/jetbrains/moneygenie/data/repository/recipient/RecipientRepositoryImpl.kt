package com.jetbrains.moneygenie.data.repository.recipient

import com.jetbrains.moneygenie.data.local.RealmManager
import com.jetbrains.moneygenie.data.models.Recipient

/**
 * Created by Kundan on 23/10/24
 **/
class RecipientRepositoryImpl(private val realmManager: RealmManager) : RecipientRepository {

    override fun addRecipient(recipient: Recipient) {
        realmManager.addObject(recipient)
    }

    override fun getRecipientById(id: String): Recipient? {
        return realmManager.getObjectById(Recipient::class, id)
    }

    override fun getAllRecipients(): List<Recipient> {
        return realmManager.getAllObjects(Recipient::class)
    }

    override fun updateRecipient(recipient: Recipient, update: Recipient.() -> Unit) {
        realmManager.updateObject(recipient, update)
    }

    override fun deleteRecipientById(id: String) {
        realmManager.deleteObjectById(Recipient::class, id)
    }
}
