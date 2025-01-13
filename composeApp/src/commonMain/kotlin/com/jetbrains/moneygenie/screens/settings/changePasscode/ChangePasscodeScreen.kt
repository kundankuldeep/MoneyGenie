package com.jetbrains.moneygenie.screens.settings.changePasscode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
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

/**
 * Created by Kundan on 12/01/25
 **/
class ChangePasscodeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: ChangePasscodeScreenModel = getScreenModel()
        ChangePasscodeScreenContent(navigator, viewModel)
        viewModel.initViews(navigator)
    }

    @Composable
    private fun ChangePasscodeScreenContent(
        navigator: Navigator,
        viewModel: ChangePasscodeScreenModel
    ) {
        val scrollState = rememberScrollState() // Manages scroll position

        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    "Change Passcode",
                    showNavigationIcon = true
                )
            },
            bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MGButton(
                        isFullWidth = false,
                        text = "Update Passcode",
                        type = MGButtonType.SOLID,
                        onClick = {
                            viewModel.onUpdatePasscodeClick()
                        })
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(it)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(scrollState), // Enable vertical scrolling
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val space = 12

                    // Show Security Question
                    Text(
                        text = "Security Question: ",
                        style = MGTypography().bodySemiBold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxSize()
                    )
                    VerticalSpace(4)
                    Text(
                        text = viewModel.securityQuestion,
                        style = MGTypography().bodyRegular
                    )

                    // Block for security Answer
                    VerticalSpace(space)
                    FloatingLabelEditText(
                        label = "Answer",
                        value = viewModel.securityAnswer,
                        onValueChange = { value -> viewModel.updateSecurityAnswer(value) },
                    )

                    // Block For new passcode
                    VerticalSpace(space)
                    FloatingLabelEditText(
                        label = "Passcode",
                        value = viewModel.passcode,
                        onValueChange = { value -> viewModel.updatePasscode(value) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        isPasswordField = true
                    )


                    // Block For confirm new passcode
                    VerticalSpace(space)
                    FloatingLabelEditText(
                        label = "Confirm Passcode",
                        value = viewModel.confirmPasscode,
                        onValueChange = { value -> viewModel.updateConfirmPasscode(value) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        isPasswordField = true
                    )
                }
            }
        }
    }
}