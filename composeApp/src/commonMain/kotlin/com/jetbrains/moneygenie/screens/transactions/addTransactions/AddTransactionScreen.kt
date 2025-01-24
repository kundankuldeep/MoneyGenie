package com.jetbrains.moneygenie.screens.transactions.addTransactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.*
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
    private val transaction: Transaction?, // Renamed `data` to `transaction`
    private val onBack: (shouldRefresh: Boolean) -> Unit,
) : Screen {

    @Composable
    override fun Content() {
        val viewModel: AddTransactionScreenModel = getScreenModel()
        LaunchedEffect(Unit) {
            viewModel.initialize(recipientId, transaction)
        }
        AddTransactionsComposable(viewModel, onBack, transaction)
    }

    @Composable
    fun AddTransactionsComposable(
        viewModel: AddTransactionScreenModel,
        onBack: (shouldRefresh: Boolean) -> Unit,
        transaction: Transaction?
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                MainAppBar(
                    navigator = navigator,
                    title = if (transaction == null) stringResource(Res.string.add_transaction_title)
                    else stringResource(Res.string.edit_transaction_title),
                    showNavigationIcon = true
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(scrollState),
            ) {
                Text(
                    text = stringResource(Res.string.add_transaction_sub_title),
                    style = MGTypography().bodyRegularL
                )

                VerticalSpace(24)

                // Transaction Amount
                FloatingLabelEditText(
                    label = "Enter the Amount",
                    value = viewModel.transactionAmount,
                    onValueChange = { viewModel.updateTransactionAmount(it) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                // Transaction Note
                FloatingLabelEditText(
                    label = "Enter Transaction Note",
                    value = viewModel.transactionNote,
                    onValueChange = { viewModel.updateTransactionNote(it) }
                )

                VerticalSpace(10)

                // Transaction Type Selection
                OweTypeChipGroup(
                    isFillMaxWidth = true,
                    selectedOption = viewModel.transactionType,
                    onSelectionChanged = { viewModel.updateTransactionType(it) }
                )

                VerticalSpace(48)

                // Save Button
                MGButton(
                    isFullWidth = true,
                    text = if (transaction == null) "Add Transaction" else "Update Transaction",
                    type = MGButtonType.SOLID,
                    onClick = { viewModel.onSaveClick(navigator, onBack) }
                )
            }
        }
    }
}
