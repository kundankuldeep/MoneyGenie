package com.jetbrains.moneygenie.screens.recipient.addRecipients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.FloatingLabelEditText
import com.jetbrains.moneygenie.components.GenderSelectionChipGroup
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.OweTypeChipGroup
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.MGTypography
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.add_outstanding_balance
import moneygenie.composeapp.generated.resources.add_recipient_sub_title
import moneygenie.composeapp.generated.resources.add_recipient_title
import org.jetbrains.compose.resources.stringResource

/**
 * Created by Kundan on 06/10/24
 **/
class AddRecipientScreen(private val onBack: (shouldRefresh: Boolean) -> Unit) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: AddRecipientScreenModel = getScreenModel()
        AddRecipientComposable(screenModel, navigator, onBack)
    }

    @Composable
    fun AddRecipientComposable(
        viewModel: AddRecipientScreenModel,
        navigator: Navigator,
        onBack: (shouldRefresh: Boolean) -> Unit
    ) {
        val scrollState = rememberScrollState() // Manages scroll position
        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator = navigator,
                    stringResource(Res.string.add_recipient_title),
                    showNavigationIcon = true
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        MGButton(
                            isFullWidth = false,
                            text = "Cancel",
                            type = MGButtonType.SOLID,
                            onClick = {
                                viewModel.navigateToDashboard(navigator, onBack, false)
                            })
                        MGButton(
                            isFullWidth = false,
                            text = "Save Recipient",
                            type = MGButtonType.SOLID,
                            onClick = {
                                viewModel.onSaveClick(navigator, onBack)
                            })
                    }

                }
            }
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
            ) {

                Text(
                    text = stringResource(Res.string.add_recipient_sub_title),
                    style = MGTypography().bodyRegularL
                )

                VerticalSpace(24)

                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    // Name field
                    FloatingLabelEditText(
                        label = "Enter recipient's name",
                        value = viewModel.recipientName,
                        onValueChange = { viewModel.updateName(it) }
                    )

                    // Number field
                    FloatingLabelEditText(
                        label = "Enter recipient's number",
                        value = viewModel.recipientNumber,
                        onValueChange = { viewModel.updateNumber(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                    )

                    // Email field
                    FloatingLabelEditText(
                        label = "Enter recipient's email",
                        value = viewModel.recipientEmail,
                        onValueChange = { viewModel.updateEmail(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                    )

                    GenderSelectionChipGroup(
                        isFillMaxWidth = true,
                        selected = viewModel.recipientGender,
                        onSelectionChanged = { viewModel.updateGender(it) }
                    )

                    // Note field
                    FloatingLabelEditText(
                        label = "Enter recipient's Note",
                        value = viewModel.recipientNote,
                        onValueChange = { viewModel.updateNote(it) }
                    )

                    VerticalSpace(24)

                    Text(
                        text = stringResource(Res.string.add_outstanding_balance),
                        style = MGTypography().bodyRegular
                    )

                    // Outstanding balance field
                    FloatingLabelEditText(
                        label = "Enter the Amount",
                        value = viewModel.outstandingBalance,
                        onValueChange = { viewModel.updateOutstandingBalance(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    VerticalSpace(12)

                    OweTypeChipGroup(
                        isFillMaxWidth = true,
                        selectedOption = viewModel.transactionType,
                        onSelectionChanged = { viewModel.updateOutstandingBalanceOwedBy(it) })

                    VerticalSpace(12)

                    // Outstanding balance field Note
                    FloatingLabelEditText(
                        label = "Enter Outstanding Balance Note",
                        value = viewModel.outstandingBalanceNote,
                        onValueChange = { viewModel.updateOutstandingBalanceNote(it) }
                    )
                }

            }
        }
    }
}
