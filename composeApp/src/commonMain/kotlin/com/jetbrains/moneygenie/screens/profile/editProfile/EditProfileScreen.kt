package com.jetbrains.moneygenie.screens.profile.editProfile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
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
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.expects.DatePickerField

/**
 * Created by Kundan on 15/12/24
 **/
class EditProfileScreen(private val onBackClick: (Boolean) -> Unit) : Screen {
    @Composable
    override fun Content() {
        val viewModel: EditProfileScreenModel = getScreenModel()
        EditProfileScreenContent(viewModel, onBackClick)
        viewModel.initViews()
    }

    @Composable
    private fun EditProfileScreenContent(
        viewModel: EditProfileScreenModel,
        onBackClick: (Boolean) -> Unit
    ) {
        val navigator = LocalNavigator.currentOrThrow

        val scrollState = rememberScrollState() // Manages scroll position

        Scaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    "Edit Profile",
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
                        isFullWidth = true,
                        text = "Update Profile",
                        type = MGButtonType.SOLID,
                        onClick = {
                            viewModel.onUpdateProfileClick(navigator, onBackClick)
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

                    GenderSelectionChipGroup(
                        isFillMaxWidth = true,
                        selected = viewModel.gender
                    ) { gender ->
                        viewModel.updateGender(gender)
                    }

                    VerticalSpace(spaceBetween)
                }
            }
        }
    }
}