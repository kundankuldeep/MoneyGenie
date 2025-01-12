package com.jetbrains.moneygenie.screens.settings.changeSecurityQuestion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.MGButton
import com.jetbrains.moneygenie.components.MGButtonType
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.VerticalSpace

/**
 * Created by Kundan on 12/01/25
 **/
class ChangeSecurityQuestionScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel: ChangeSecurityQuestionScreenModel = getScreenModel()
        ChangeSecurityQuestionScreenContent(viewModel)
        viewModel.initViews()
    }

    @Composable
    private fun ChangeSecurityQuestionScreenContent(
        viewModel: ChangeSecurityQuestionScreenModel
    ) {
        val navigator = LocalNavigator.currentOrThrow

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
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 0.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    MGButton(
                        isFullWidth = true,
                        text = "Update Security Question",
                        type = MGButtonType.SOLID,
                        onClick = {

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



                    VerticalSpace(spaceBetween)
                }
            }
        }
    }
}