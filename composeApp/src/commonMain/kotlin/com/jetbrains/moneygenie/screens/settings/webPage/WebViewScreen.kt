package com.jetbrains.moneygenie.screens.settings.webPage

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.MGScaffold
import com.jetbrains.moneygenie.components.MainAppBar
import com.jetbrains.moneygenie.expects.LoadWebView

/**
 * WebViewScreen to display the URL in a WebView and show the title in the app bar.
 */
class WebViewScreen(private val url: String, private val screenTitle: String) : Screen {

    @Composable
    override fun Content() {
        val viewModel: WebViewScreenModel =
            getScreenModel() // This is optional if you need to use ViewModel here
        WebViewScreenComposable(url, screenTitle)
    }

    @Composable
    private fun WebViewScreenComposable(url: String, screenTitle: String) {
        val navigator = LocalNavigator.currentOrThrow

        MGScaffold(
            topBar = {
                MainAppBar(
                    navigator,
                    screenTitle,  // Display the title on the app bar
                    showNavigationIcon = true,
                )
            }
        ) {
            LoadWebView(url)
        }
    }
}
