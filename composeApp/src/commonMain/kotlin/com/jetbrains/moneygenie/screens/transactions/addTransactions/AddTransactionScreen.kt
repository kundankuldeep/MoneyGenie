package com.jetbrains.moneygenie.screens.transactions.addTransactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.FloatingLabelEditText
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.OweTypeChipGroup
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.data.models.Transaction
import com.jetbrains.moneygenie.theme.MGTypography
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.add_transaction_sub_title
import moneygenie.composeapp.generated.resources.add_transaction_title
import moneygenie.composeapp.generated.resources.edit_transaction_title
import org.jetbrains.compose.resources.stringResource

/**
 * Created by Kundan on 15/12/24
 **/
class AddTransactionScreen(
    private val recipientId: Long,
    private val data: Transaction?,
    private val onBack: (shouldRefresh: Boolean) -> Unit,
) :
    Screen {
    @Composable
    override fun Content() {
        val viewModel: AddTransactionScreenModel = getScreenModel()
        viewModel.initialize(recipientId, data)
        AddTransactionsComposable(viewModel, onBack, data)
    }

    @Composable
    fun AddTransactionsComposable(
        viewModel: AddTransactionScreenModel,
        onBack: (shouldRefresh: Boolean) -> Unit,
        data: Transaction?
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val scrollState = rememberScrollState() // Manages scroll position

        Scaffold(
            topBar = {
                MainAppBar(
                    navigator = navigator,
                    if (data == null) stringResource(Res.string.add_transaction_title) else stringResource(
                        Res.string.edit_transaction_title
                    ),
                    showNavigationIcon = true
                )
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {

                Text(
                    text = stringResource(Res.string.add_transaction_sub_title),
                    style = MGTypography().bodyRegularL
                )

                VerticalSpace(24)

                Column(modifier = Modifier.verticalScroll(scrollState)) {

                    // Transaction amount field
                    FloatingLabelEditText(
                        label = "Enter the Amount",
                        value = viewModel.transactionBalance,
                        onValueChange = { viewModel.updateTransactionBalance(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    // Transaction note field
                    FloatingLabelEditText(
                        label = "Enter Transaction Note",
                        value = viewModel.transactionBalanceNote,
                        onValueChange = { viewModel.updateTransactionBalanceNote(it) }
                    )

                    VerticalSpace(10)

                    // Transaction type field
                    OweTypeChipGroup(
                        isFillMaxWidth = true,
                        onSelectionChanged = { viewModel.updateTransactionBalanceOwedBy(it.value) })

                    VerticalSpace(48)

                    // save button
                    MGButton(
                        isFullWidth = true,
                        text = if (data == null) "Add Transaction" else "Update Transaction",
                        type = MGButtonType.SOLID,
                        onClick = {
                            viewModel.onSaveClick(navigator, onBack)
                        })
                }

            }
        }
    }
}