package com.jetbrains.moneygenie.screens.transactions

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.components.VerticalSpace

/**
 * Created by Kundan on 12/01/25
 **/
class TransactionScreen(recipientId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: TransactionScreenModel = getScreenModel()
        TransactionScreenContent(navigator, viewModel)
        viewModel.initViews(navigator)
    }

    @Composable
    private fun TransactionScreenContent(
        navigator: Navigator,
        viewModel: TransactionScreenModel
    ) {
        val scrollState = rememberScrollState() // Manages scroll position

        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    "Transactions",
                    showNavigationIcon = true
                )
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