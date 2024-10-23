package com.jetbrains.moneygenie.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
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
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.screens.components.CustomTextField
import com.jetbrains.moneygenie.screens.components.GradientIconButton
import com.jetbrains.moneygenie.screens.components.SelectableChip
import com.jetbrains.moneygenie.screens.home.HomeScreen
import com.jetbrains.moneygenie.screens.list.ListScreen

/**
 * Created by Kundan on 18/08/24
 **/
class OnBoardingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: OnBoardingScreenModel = getScreenModel()

        // check if user is logged in or not
        if (screenModel.isLoggedIn()) {
            navigator.replace(ListScreen)
        } else {
            navigator.replace(HomeScreen)
        }
    }

    @Composable
    private fun OnBoardingContent() {
        Box(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            Column {
                Text("Hello!!")
                Text("Welcome to Money Genie, Lets make len den simple.")
                CustomTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = "Enter your name"
                )
                GenderSelectionChipGroup()
                GradientIconButton(icon = Icons.Rounded.Add)
            }
        }
    }
}

@Composable
fun GenderSelectionChipGroup(
    modifier: Modifier = Modifier
) {
    var selectedGender by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectableChip(
            text = "Male",
            isSelected = selectedGender == "Male",
            onSelectionChanged = {
                selectedGender = if (selectedGender == "Male") null else "Male"
            }
        )

        SelectableChip(
            text = "Female",
            isSelected = selectedGender == "Female",
            onSelectionChanged = {
                selectedGender = if (selectedGender == "Female") null else "Female"
            }
        )
    }
}