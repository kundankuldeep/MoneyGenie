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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.DropdownComposable
import com.jetbrains.moneygenie.components.FloatingLabelEditText
import com.jetbrains.moneygenie.components.GenderSelectionChipGroup
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.expects.DatePickerField
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Natural500
import com.jetbrains.moneygenie.theme.Primary700
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

        Scaffold(
            bottomBar = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MGButton(
                        isFullWidth = true,
                        text = "Create Account",
                        type = MGButtonType.SOLID,
                        onClick = {
                            viewModel.onSignUpClick(navigator)
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
                            value = viewModel.fullName,
                            onValueChange = { value -> viewModel.updateFullName(value) }
                        )

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Email",
                            value = viewModel.email,
                            onValueChange = { value -> viewModel.updateEmail(value) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Phone Number",
                            value = viewModel.phone,
                            onValueChange = { value -> viewModel.updatePhone(value) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                        )

                        DatePickerField(
                            label = "Date Of Birth",
                            value = viewModel.dob,
                            onValueChange = { value -> viewModel.updateDob(value) }
                        )

                        VerticalSpace(spaceBetween)

                        GenderSelectionChipGroup(isFillMaxWidth = true) { gender ->
                            viewModel.updateGender(gender.value)
                        }

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Passcode",
                            value = viewModel.passcode,
                            onValueChange = { value -> viewModel.updatePasscode(value) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            isPasswordField = true
                        )

                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Confirm Passcode",
                            value = viewModel.confirmPasscode,
                            onValueChange = { value -> viewModel.updateConfirmPasscode(value) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            isPasswordField = true
                        )
                        VerticalSpace(spaceBetween)

                        Text(
                            "To help you recover your passcode, please answer the question below.",
                            color = Natural500,
                            style = MGTypography().bodyRegular
                        )

                        VerticalSpace(spaceBetween)

                        DropdownComposable(
                            options = viewModel.getQuestions(),
                            selectedOption = viewModel.securityQuestion,
                            onOptionSelected = { sq -> viewModel.securityQuestion = sq },
                            label = "Select a question"
                        )
                        VerticalSpace(spaceBetween)

                        FloatingLabelEditText(
                            label = "Answer",
                            value = viewModel.securityAnswer,
                            onValueChange = { value -> viewModel.updateSecurityAnswer(value) },
                        )
                        VerticalSpace(spaceBetween)

                        ClickableTermsAndPrivacy(
                            onTermsClick = { /* Navigate to Terms of Service screen */ },
                            onPrivacyClick = { /* Navigate to Privacy Policy screen */ }
                        )

                        VerticalSpace(spaceBetween)
                    }
                }
            }
        }
    }
}

@Composable
fun ClickableTermsAndPrivacy(
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedText = buildAnnotatedString {
        append("By creating an account, you agree to our ")

        // Terms of Service
        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(
            style = SpanStyle(
                color = Natural500,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Terms of Service")
        }
        pop()

        append(" and ")

        // Privacy Policy
        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
        withStyle(
            style = SpanStyle(
                color = Natural500,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append("Privacy Policy")
        }
        pop()

        append(".")
    }

    ClickableText(
        text = annotatedText,
        modifier = modifier,
        style = MGTypography().bodyRegular.copy(color = Natural500),
        onClick = { offset ->
            annotatedText.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    when (annotation.tag) {
                        "TERMS" -> onTermsClick()
                        "PRIVACY" -> onPrivacyClick()
                    }
                }
        }
    )
}
