package com.jetbrains.moneygenie.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary700
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.logo_green
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 01/01/25
 **/
class LoginScreen : Screen {

    @Composable
    override fun Content() {
        LoginScreenContent()
    }

    @Composable
    private fun LoginScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: LoginScreenModel = getScreenModel()
        val scrollState = rememberScrollState() // Manages scroll position

        Scaffold() {
            Box(modifier = Modifier.fillMaxSize().padding(it)) {
                // top title
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {

                    // Header content
                    Column(modifier = Modifier.padding(20.dp)) {
                        // logo icon
                        Image(
                            painter = painterResource(Res.drawable.logo_green), // Replace with your image
                            contentDescription = null,
                            modifier = Modifier.width(65.dp).height(35.dp)
                        )
                        VerticalSpace(30)

                        // screen Title
                        Text(
                            text = "Welcome Back!",
                            color = Primary700,
                            style = MGTypography().titleBoldL
                        )
                        VerticalSpace(10)

                        // screen Title
                        Text(
                            text = "Please enter your passcode to securely access your financial dashboard.",
                            color = Natural500,
                            style = MGTypography().bodyRegularL
                        )

                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                            .verticalScroll(scrollState), // Enable vertical scrolling
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val spaceBetween = 6

                        FloatingLabelEditText(
                            label = "Passcode",
                            value = viewModel.passcode,
                            onValueChange = { value -> viewModel.updatePasscode(value) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            isPasswordField = true
                        )

                        VerticalSpace(spaceBetween)

                        MGButton(
                            isFullWidth = true,
                            text = "Continue",
                            type = MGButtonType.SOLID,
                            onClick = {
                                viewModel.onLoginClick(navigator)
                            })

                        VerticalSpace(spaceBetween)
                    }
                }
            }
        }
    }
}