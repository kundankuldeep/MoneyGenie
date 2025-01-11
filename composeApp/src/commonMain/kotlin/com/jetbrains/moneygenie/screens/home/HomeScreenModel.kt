package com.jetbrains.moneygenie.screens.home

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.jetbrains.moneygenie.data.models.Recipient
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.data.repository.recipient.RecipientRepository
import com.jetbrains.moneygenie.data.repository.transaction.TransactionRepository
import com.jetbrains.moneygenie.screens.bottomSheets.HomeOptionsBS
import com.jetbrains.moneygenie.screens.profile.ProfileScreen
import com.jetbrains.moneygenie.screens.recipient.RecipientScreen
import com.jetbrains.moneygenie.screens.recipient.addRecipients.AddRecipientScreen
import com.jetbrains.moneygenie.screens.transactions.AddTransactionScreen
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.math.abs

/**
 * Created by Kundan on 26/09/24
 **/
class HomeScreenModel : ScreenModel, KoinComponent {
    private val recipientRepository: RecipientRepository by inject()
    private val transactionRepository: TransactionRepository by inject()

    val userName = mutableStateOf("Guest")
    val totalLent = mutableStateOf(0.0)
    val totalBorrowed = mutableStateOf(0.0)
    val dataList = mutableStateOf<ArrayList<RecipientViewItem>>(arrayListOf())

    val searchText = mutableStateOf("")

    private var recipients: List<Recipient>? = null

    fun updateSearchText(value: String) {
        searchText.value = value
    }

    fun initViewModel() {
        getDataForRecipients()
        getAccountsOverallData()
        getUserName()
    }

    private fun getAccountsOverallData() {
        screenModelScope.launch {
            totalLent.value = transactionRepository.getAllLentAmount()
            totalBorrowed.value = transactionRepository.getAllBorrowedAmount()
        }
    }

    private fun getUserName() {
        screenModelScope.launch {
            PreferenceManager.getPreference(PreferenceKeys.USERNAME)?.let {
                userName.value = it.split(" ").getOrNull(0) ?: ""
            }
        }
    }

    private fun getDataForRecipients() {
        println("getDataForRecipients is called")
        dataList.value.clear()
        screenModelScope.launch {
            recipients = recipientRepository.getAllRecipients()
            val latestTransactions = transactionRepository.getLatestTransactionByRecipient()
            recipients?.let {
                dataList.value.addAll(getViewItemList(it, latestTransactions))
            }
        }
    }

    private fun getViewItemList(
        recipients: List<Recipient>,
        latestTransactions: List<Transaction>
    ): ArrayList<RecipientViewItem> {
        val list = arrayListOf<RecipientViewItem>()

        recipients.forEach { recipient ->
            fun lastUpdatedDate(): Long {
                val lastTransaction =
                    latestTransactions.firstOrNull { it.recipientId == recipient.id }
                return lastTransaction?.modifiedDate ?: recipient.updatedDate
            }

            val amount = recipient.totalLent - recipient.totalBorrowed

            list.add(
                RecipientViewItem(
                    recipientId = recipient.id,
                    recipientName = recipient.name,
                    recipientNote = recipient.note ?: "",
                    amount = abs(amount),
                    status = if (amount >= 0) OverAllStatus.YouOwe else OverAllStatus.TheyOwe,
                    lastUpdatedDate = lastUpdatedDate(),
                )
            )
        }

        return list
    }

    fun onAddRecipientClick(navigator: Navigator) {
        navigator.push(AddRecipientScreen {
            if (it) {
                refreshDashboard()
            }
        })
    }

    fun onMoreIconClick(bottomSheetNavigator: BottomSheetNavigator, navigator: Navigator) {
        bottomSheetNavigator.show(HomeOptionsBS(navigator))
    }

    private fun getRecipientBy(recipientIs: Long): Recipient? {
        return recipients?.firstOrNull { it.id == recipientIs }
    }

    fun onRecipientItemClick(recipient: RecipientViewItem, navigator: Navigator) {
        println("onRecipientItemClick ${recipient.recipientName}")
        getRecipientBy(recipient.recipientId)?.let {
            navigator.push(RecipientScreen({ isRefresh ->
                if (isRefresh) refreshDashboard()
            }, it))
        }
    }

    fun onAddTransactionClick(recipient: RecipientViewItem, navigator: Navigator) {
        println("onAddTransactionClick ${recipient.recipientName}")
        navigator.push(AddTransactionScreen({
            if (it) refreshDashboard()
        }, recipient.recipientId, null))
    }

    private fun refreshDashboard() {
        screenModelScope.launch {
            getDataForRecipients()
            getAccountsOverallData()
        }
    }

    fun onProfileIconClicked(navigator: Navigator) {
        navigator.push(ProfileScreen())
    }
}

