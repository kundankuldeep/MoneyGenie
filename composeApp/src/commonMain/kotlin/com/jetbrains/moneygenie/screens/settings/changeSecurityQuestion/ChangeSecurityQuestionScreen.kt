package com.jetbrains.moneygenie.screens.settings.changeSecurityQuestion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
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
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Primary700
import com.jetbrains.moneygenie.utils.MGConstants

/**
 * Created by Kundan on 12/01/25
 **/
class ChangeSecurityQuestionScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ChangeSecurityQuestionScreenModel = getScreenModel()
        ChangeSecurityQuestionScreenContent(navigator, viewModel)
        viewModel.initViews(navigator)
    }

    @Composable
    private fun ChangeSecurityQuestionScreenContent(
        navigator: Navigator,
        viewModel: ChangeSecurityQuestionScreenModel
    ) {
        val scrollState = rememberScrollState() // Manages scroll position

        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    "Change Security Question",
                    showNavigationIcon = true
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MGButton(
                        isFullWidth = false,
                        text = "Update Security Question",
                        type = MGButtonType.SOLID,
                        onClick = {
                            viewModel.onUpdateSecurityQuestionClicked()
                        })
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .verticalScroll(scrollState), // Enable vertical scrolling
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val spaceBetween = 12

                    // Enter the passcode
                    FloatingLabelEditText(
                        label = "Passcode",
                        value = viewModel.passcode,
                        onValueChange = { value -> viewModel.updatePasscode(value) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        isPasswordField = true
                    )

                    VerticalSpace(spaceBetween)
                    // change the question
                    RadioGroup(
                        options = MGConstants.getQuestions(),
                        selectedOption = viewModel.securityQuestion
                    ) { newQtn ->
                        viewModel.updateSecurityQuestion(newQtn)
                    }

                    VerticalSpace(spaceBetween)
                    FloatingLabelEditText(
                        label = "Answer",
                        value = viewModel.securityAnswer,
                        onValueChange = { value -> viewModel.updateSecurityAnswer(value) },
                    )

                    VerticalSpace(spaceBetween)
                }
            }
        }
    }
}

@Composable
fun RadioGroup(
    options: List<String>, // List of options for the radio group
    selectedOption: String? = null, // Default selected option, nullable
    onOptionSelected: (String) -> Unit // Callback for when an option is selected
) {
    Column {
        // Display the options
        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
                    .clickable { onOptionSelected(option) } // Update via callback on click
            ) {
                RadioButton(
                    selected = (selectedOption == option),
                    onClick = { onOptionSelected(option) }, // Update via callback on button click
                    colors = RadioButtonDefaults.colors(selectedColor = Primary700)
                )
                Text(
                    text = option,
                    style = MGTypography().bodyRegular,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
