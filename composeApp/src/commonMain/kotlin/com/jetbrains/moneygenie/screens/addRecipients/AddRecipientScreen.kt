package com.jetbrains.moneygenie.screens.addRecipients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.GenderSelectionChipGroup
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.FloatingLabelEditText
import com.jetbrains.moneygenie.theme.MGTypography
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.add_recipient_sub_title
import moneygenie.composeapp.generated.resources.add_recipient_title
import moneygenie.composeapp.generated.resources.add_outstanding_balance
import org.jetbrains.compose.resources.stringResource

/**
 * Created by Kundan on 06/10/24
 **/
class AddRecipientScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: AddRecipientScreenModel = getScreenModel()
        AddRecipientComposable(screenModel, navigator)
    }
}

@Composable
fun AddRecipientComposable(viewModel: AddRecipientScreenModel, navigator: Navigator) {
    // State variables for storing user inputs
    var name by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    // State for error checking
    var nameError by remember { mutableStateOf(false) }
    var contactError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MainAppBar(
                navigator = navigator,
                stringResource(Res.string.add_recipient_title),
                showNavigationIcon = true
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = stringResource(Res.string.add_recipient_sub_title),
                    style = MGTypography().bodyRegular
                )

                // Name field
                FloatingLabelEditText(
                    label = "Enter recipient's name",
                    value = name,
                    onValueChange = { name = it }
                )

                // Number field
                FloatingLabelEditText(
                    label = "Enter recipient's number",
                    value = name,
                    onValueChange = { name = it }
                )

                // Email field
                FloatingLabelEditText(
                    label = "Enter recipient's email",
                    value = name,
                    onValueChange = { name = it }
                )

                GenderSelectionChipGroup()

                // Note field
                FloatingLabelEditText(
                    label = "Enter recipient's email",
                    value = name,
                    onValueChange = { name = it }
                )

                Text(
                    text = stringResource(Res.string.add_outstanding_balance),
                    style = MGTypography().bodyRegular
                )

                // Note field
                FloatingLabelEditText(
                    label = "Enter the Amount",
                    value = name,
                    onValueChange = { name = it }
                )

                GenderSelectionChipGroup()

                // Submit button
                Button(
                    onClick = {
                        nameError = name.isEmpty()
                        contactError = contact.isEmpty()

                        if (!nameError && !contactError) {
                            // Perform the submit action, like saving recipient info
                            println("Recipient Added: $name, $contact, $email, $gender")
                            viewModel.saveRecipient(name, contact, email, gender)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Submit")
                }
            }
        }
    )
}
