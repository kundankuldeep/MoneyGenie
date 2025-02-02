package com.jetbrains.moneygenie.screens.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.ConfirmationDialogData
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.ShowConfirmationDialog
import com.jetbrains.moneygenie.components.TransactionFilters
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.screens.recipient.TransactionItem
import com.jetbrains.moneygenie.screens.recipient.TransactionItemCallback

/**
 * Created by Kundan on 12/01/25
 **/
class TransactionScreen(private val recipientId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: TransactionScreenModel = getScreenModel()
        TransactionScreenContent(navigator, viewModel)
        viewModel.initViews(navigator, recipientId)
    }

    @Composable
    private fun TransactionScreenContent(
        navigator: Navigator,
        viewModel: TransactionScreenModel
    ) {
        val transactions by viewModel.transactions.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()
        val errorMessage by viewModel.errorMessage.collectAsState()

        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    "Transactions",
                    showNavigationIcon = true
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 🔹 Filter Buttons
                    Box(modifier = Modifier.padding(bottom = 12.dp).fillMaxWidth()) {
                        TransactionFilters(
                            selectedFilter = viewModel.selectedFilter.collectAsState().value,
                            onSelectionChanged = { selectedFilter ->
                                viewModel.updateFilter(
                                    selectedFilter
                                )
                            }
                        )
                    }

                    VerticalSpace(8)

                    // 🔹 Transaction List with state handling
                    when {
                        isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                        errorMessage != null -> Text(
                            "Error: $errorMessage",
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        transactions.isEmpty() -> Text(
                            "No transactions available",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        else -> TransactionList(transactions, viewModel)
                    }
                }

                if (viewModel.showDeleteTransactionDialog) {
                    ShowConfirmationDialog(
                        data = ConfirmationDialogData(
                            "Delete Transaction",
                            "Are you sure you want to delete this transaction?"
                        ),
                        onConfirm = {
                            viewModel.showDeleteTransactionDialog = false
                            viewModel.onDeleteTransactionClick()
                        }, onDismiss = {
                            viewModel.showDeleteTransactionDialog = false
                        })
                }
            }
        }
    }

    // 🔹 Transaction List
    @Composable
    fun TransactionList(transactions: List<Transaction>, viewModel: TransactionScreenModel) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(transactions) { transaction ->
                TransactionItem({ transactionItemCallback ->
                    when (transactionItemCallback) {
                        TransactionItemCallback.DeleteTransaction -> {
                            // Handle delete action
                            viewModel.currentTransaction = transaction
                            viewModel.showDeleteTransactionDialog = true
                        }

                        TransactionItemCallback.EditTransaction -> {
                            // Handle edit action
                            viewModel.onEditTransactionClick(transaction)
                        }
                    }
                }, transaction)
                VerticalSpace(8)
            }
        }
    }
}
