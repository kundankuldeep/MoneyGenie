package com.jetbrains.moneygenie.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.jetbrains.moneygenie.components.MainAppBar

/**
 * Created by Kundan on 15/12/24
 **/
class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: SettingsScreenModel = getScreenModel()
        SettingsScreenComposable(screenModel, navigator)
    }
}

@Composable
fun SettingsScreenComposable(screenModel: SettingsScreenModel, navigator: Navigator) {
    Scaffold(
        topBar = {
            MainAppBar(
                navigator,
                "Settings",
                showNavigationIcon = true
            )
        }
    ) {
        Box(Modifier.fillMaxSize().padding(it)) {
            Text(text = "This is Settings")
        }
    }
}