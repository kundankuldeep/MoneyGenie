package com.jetbrains.moneygenie.data.repository.recipient

import com.jetbrains.moneygenie.data.models.Recipient

/**
 * Created by Kundan on 23/10/24
 **/

interface RecipientRepository {
    fun addRecipient(recipient: Recipient)
    fun getRecipientById(id: Long): Recipient?
    fun getAllRecipients(): List<Recipient>
    fun updateRecipient(recipient: Recipient, update: Recipient.() -> Unit)
    fun deleteRecipientById(id: String)
}