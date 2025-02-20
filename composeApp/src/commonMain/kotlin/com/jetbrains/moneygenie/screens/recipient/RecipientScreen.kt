package com.jetbrains.moneygenie.screens.recipient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.AccountStatusCard
import com.jetbrains.moneygenie.components.ConfirmationDialogData
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.ShowConfirmationDialog
import com.jetbrains.moneygenie.components.TransactionType
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.theme.Error700
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural100
import com.jetbrains.moneygenie.theme.Natural300
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary700
import com.jetbrains.moneygenie.utils.DateTimeUtils
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.ic_delete
import moneygenie.composeapp.generated.resources.ic_edit
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 15/12/24
 **/
class RecipientScreen(private val recipientId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: RecipientScreenModel = getScreenModel()
        viewModel.initViews(navigator, recipientId)
        RecipientScreenComposable(navigator, viewModel)
    }

    @Composable
    fun RecipientScreenComposable(navigator: Navigator, viewModel: RecipientScreenModel) {
        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    viewModel.recipient?.name ?: "",
                    showNavigationIcon = true,
                    actions = {
                        IconButton(onClick = {
                            viewModel.onEditRecipientClick()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Edit Icon",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                            viewModel.showDeleteAccountDialog = true
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White
                            )
                        }
                    }
                )
            }
        ) {
            Box(
                Modifier.fillMaxSize().padding(it).padding(top = 20.dp, start = 20.dp, end = 20.dp)
            ) {
                Column {
                    AccountStatusCard(viewModel.totalLent.value, viewModel.totalBorrowed.value)

                    VerticalSpace(26)

                    MGButton(
                        isFullWidth = true,
                        text = "+ Add transaction",
                        type = MGButtonType.SOLID,
                        onClick = {
                            viewModel.onAddTransactionClick()
                        })

                    VerticalSpace(26)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MGButton(
                            isFullWidth = false,
                            text = "Settle Account",
                            type = MGButtonType.OUTLINE,
                            onClick = {
                                viewModel.showSettleAccountDialog = true
                            })
                        MGButton(
                            isFullWidth = false,
                            text = "Remind Them",
                            type = MGButtonType.OUTLINE,
                            onClick = {
                                viewModel.onRemindThemClick()
                            })
                    }

                    VerticalSpace(26)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Transaction History",
                            style = MGTypography().bodyRegularL,
                            color = Natural500
                        )
                        Text(
                            "View All",
                            style = MGTypography().bodySemiBoldS,
                            color = Primary700,
                            modifier = Modifier.clickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null
                            ) {
                                viewModel.onViewAllTransactionsClick()
                            })
                    }

                    VerticalSpace(12)

                    if (viewModel.transactions.value.isNotEmpty()) {
                        // show list of transactions
                        TransactionListView(viewModel)
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No Transactions")
                        }
                    }

                }

                if (viewModel.showSettleAccountDialog) {
                    ShowConfirmationDialog(
                        data = ConfirmationDialogData(
                            "Settle Account",
                            "Are you sure you want to settle this account?"
                        ),
                        onConfirm = {
                            viewModel.showSettleAccountDialog = false
                            viewModel.onSettleAccountClick()
                        }, onDismiss = {
                            viewModel.showSettleAccountDialog = false
                        })
                }

                if (viewModel.showDeleteAccountDialog) {
                    ShowConfirmationDialog(
                        data = ConfirmationDialogData(
                            "Delete Account",
                            "Are you sure you want to delete this recipient?"
                        ),
                        onConfirm = {
                            viewModel.showDeleteAccountDialog = false
                            viewModel.onDeleteRecipientClick()
                        }, onDismiss = {
                            viewModel.showDeleteAccountDialog = false
                        })
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

    @Composable
    fun TransactionListView(viewModel: RecipientScreenModel) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.transactions.value.size) { index ->
                val transaction = viewModel.transactions.value[index]
                TransactionItem({ transactionItemCallback ->
                    when (transactionItemCallback) {
                        TransactionItemCallback.DeleteTransaction -> {
                            viewModel.currentTransaction = transaction
                            viewModel.showDeleteTransactionDialog = true
                        }

                        TransactionItemCallback.EditTransaction -> {
                            viewModel.onEditTransactionClick(transaction)
                        }
                    }
                }, transaction)
                VerticalSpace(10)
            }
        }
    }
}

@Composable
fun TransactionItem(callBack: ((TransactionItemCallback) -> Unit), transaction: Transaction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(width = 1.dp, color = Natural100)
    ) {
        Box(modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Amount: ${transaction.amount}",
                        style = MGTypography().bodySemiBoldL,
                        color = if (transaction.type == TransactionType.Lent.value) Primary700 else Color.Red
                    )
                    VerticalSpace(6)
                    Text(
                        text = "Note: ${transaction.note}",
                        style = MGTypography().bodyRegular,
                        color = Natural500
                    )
                    VerticalSpace(6)
                    Text(
                        text = "Date: ${DateTimeUtils.getFormattedDate(transaction.modifiedDate)}",
                        style = MGTypography().bodyRegularS,
                        color = Natural300
                    )
                }
                Column {
                    Icon(
                        painter = painterResource(Res.drawable.ic_edit),
                        contentDescription = "Edit Icon",
                        tint = Primary700,
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            callBack.invoke(TransactionItemCallback.EditTransaction)
                        }
                    )
                    VerticalSpace(10)
                    Icon(
                        painter = painterResource(Res.drawable.ic_delete),
                        contentDescription = "Delete Icon",
                        tint = Error700,
                        modifier = Modifier.clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = null
                        ) {
                            callBack.invoke(TransactionItemCallback.DeleteTransaction)
                        }
                    )
                }
            }
        }
    }
}

sealed class TransactionItemCallback {
    data object EditTransaction : TransactionItemCallback()
    data object DeleteTransaction : TransactionItemCallback()

}