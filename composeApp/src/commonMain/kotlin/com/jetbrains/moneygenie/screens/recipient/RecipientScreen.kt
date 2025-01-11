package com.jetbrains.moneygenie.screens.recipient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.AccountStatusCard
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.TransactionType
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.data.models.Recipient
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
class RecipientScreen(
    private val onBack: (shouldRefresh: Boolean) -> Unit,
    private val recipient: Recipient
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: RecipientScreenModel = getScreenModel()
        viewModel.initViews(recipient)
        RecipientScreenComposable(viewModel)
    }

    @Composable
    fun RecipientScreenComposable(viewModel: RecipientScreenModel) {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    viewModel.recipient?.name ?: "",
                    showNavigationIcon = true,
                    actions = {
                        IconButton(onClick = {
                            viewModel.onEditRecipientClick(navigator)
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = "Edit Icon",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {
                            viewModel.onDeleteRecipientClick(navigator)
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
                            viewModel.onAddTransactionClick(onBack, navigator)
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
                                viewModel.onSettleAccountClick()
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
                        Text("View All", style = MGTypography().bodySemiBoldS, color = Primary700)
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
            }
        }
    }

    @Composable
    fun TransactionListView(viewModel: RecipientScreenModel) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.transactions.value.size) { index ->
                TransactionItem(viewModel.transactions.value[index])
                VerticalSpace(10)
            }
        }
    }

    @Composable
    fun TransactionItem(transaction: Transaction) {
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
                            modifier = Modifier.clickable { }
                        )
                        VerticalSpace(10)
                        Icon(
                            painter = painterResource(Res.drawable.ic_delete),
                            contentDescription = "Edit Icon",
                            tint = Error700,
                            modifier = Modifier.clickable { }
                        )
                    }
                }
            }
        }
    }
}