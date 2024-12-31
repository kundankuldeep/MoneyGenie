package com.jetbrains.moneygenie.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.FloatingLabelEditText
import com.jetbrains.moneygenie.components.GenderSelectionChipGroup
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.screens.home.HomeScreen
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary700
import kotlinx.coroutines.launch
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.logo_green
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 28/11/24
 **/
class SignUpScreen : Screen {
    @Composable
    override fun Content() {
        SignUpScreenContent()
    }

    @Composable
    private fun SignUpScreenContent() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: SignUpScreenModel = getScreenModel()
        val scrollState = rememberScrollState() // Manages scroll position
        var textValue by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()

        Scaffold(
            bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MGButton(
                        isFullWidth = true,
                        text = "Sign Up",
                        type = MGButtonType.SOLID,
                        onClick = {
                            // save logged in to preferences
                            coroutineScope.launch {
                                PreferenceManager.savePreference(PreferenceKeys.IS_LOGGED_IN, true)
                            }
                            navigator.push(HomeScreen)
                        })
                }
            }
        ) {
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
                            text = "Create Your Account",
                            color = Primary700,
                            style = MGTypography().titleBoldL
                        )
                        VerticalSpace(10)

                        // screen Title
                        Text(
                            text = "Join MoneyGenie to start tracking your transactions.",
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
                            label = "Full name",
                            value = textValue,
                            onValueChange = { textValue = it }
                        )

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Email",
                            value = textValue,
                            onValueChange = { textValue = it }
                        )

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Phone Number",
                            value = textValue,
                            onValueChange = { textValue = it }
                        )

                        FloatingLabelEditText(
                            label = "Date Of Birth",
                            value = textValue,
                            onValueChange = { textValue = it }
                        )

                        VerticalSpace(spaceBetween)

                        GenderSelectionChipGroup(isFillMaxWidth = true) {

                        }

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Passcode",
                            value = textValue,
                            onValueChange = { textValue = it }
                        )

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Confirm Passcode",
                            value = textValue,
                            onValueChange = { textValue = it }
                        )

                        VerticalSpace(spaceBetween)
                    }
                }
            }
        }
    }
}