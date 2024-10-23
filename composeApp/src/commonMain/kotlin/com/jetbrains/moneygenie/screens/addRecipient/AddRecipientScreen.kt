package com.jetbrains.moneygenie.screens.addRecipient

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import com.jetbrains.moneygenie.composableCollection.MainAppBar
import com.jetbrains.moneygenie.screens.onboarding.GenderSelectionChipGroup

/**
 * Created by Kundan on 06/10/24
 **/
class AddRecipientScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: AddRecipientScreenModel = getScreenModel()
        AddRecipientComposable(viewModel)
    }
}

@Composable
fun AddRecipientComposable(viewModel: AddRecipientScreenModel) {
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
                content = { Text("Add Recipient") },
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
                // Name field
                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                        nameError = it.isEmpty()  // Validate
                    },
                    label = { Text("Name *") },
                    isError = nameError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                if (nameError) {
                    Text(text = "Name is required", color = Color.Red)
                }

                // Contact field
                OutlinedTextField(
                    value = contact,
                    onValueChange = {
                        contact = it
                        contactError = it.isEmpty()  // Validate
                    },
                    label = { Text("Contact *") },
                    isError = contactError,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
                if (contactError) {
                    Text(text = "Contact is required", color = Color.Red)
                }

                // Email field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
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
