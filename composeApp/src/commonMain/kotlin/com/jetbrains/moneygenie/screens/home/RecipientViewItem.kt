package com.jetbrains.moneygenie.screens.home

/**
 * Created by Kundan on 03/01/25
 **/
data class RecipientViewItem(
    val recipientId: Long,
    val recipientName: String,
    val recipientNote: String,
    val amount: Double,
    val status: OverAllStatus,
    val lastUpdatedDate: Long
)

enum class OverAllStatus(val value: String) {
    YouOwe("You Owe"),
    TheyOwe("They Owe")
}