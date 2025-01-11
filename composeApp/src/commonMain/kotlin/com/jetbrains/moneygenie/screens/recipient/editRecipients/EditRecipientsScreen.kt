package com.jetbrains.moneygenie.screens.recipient.editRecipients

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.FloatingLabelEditText
import com.jetbrains.moneygenie.components.GenderSelectionChipGroup
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.data.models.Recipient
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.edit_recipient_title
import org.jetbrains.compose.resources.stringResource

/**
 * Created by Kundan on 10/01/25
 **/
class EditRecipientsScreen(
    private val recipient: Recipient,
    private val onBack: (shouldRefresh: Boolean) -> Unit
) :
    Screen {

    @Composable
    override fun Content() {
        val viewModel: EditRecipientsScreenModel = getScreenModel()
        viewModel.initViews(recipient)
        EditRecipientsScreenComposable(viewModel, onBack)
    }

    @Composable
    fun EditRecipientsScreenComposable(
        viewModel: EditRecipientsScreenModel,
        onBack: (shouldRefresh: Boolean) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val scrollState = rememberScrollState() // Manages scroll position
        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator = navigator,
                    stringResource(Res.string.edit_recipient_title),
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
                            isFullWidth = true,
                            text = "Update Recipient",
                            type = MGButtonType.SOLID,
                            onClick = {
                                viewModel.onUpdateClick(navigator, onBack)
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
                        onSelectionChanged = { viewModel.updateGender(it.value) }
                    )

                    // Note field
                    FloatingLabelEditText(
                        label = "Enter recipient's Note",
                        value = viewModel.recipientNote,
                        onValueChange = { viewModel.updateNote(it) }
                    )

                    VerticalSpace(24)

                }

            }
        }
    }

}