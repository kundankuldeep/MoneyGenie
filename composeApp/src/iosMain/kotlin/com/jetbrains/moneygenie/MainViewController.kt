package com.jetbrains.moneygenie

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.jetbrains.moneygenie.data.preferences.createDataStore

fun MainViewController() = ComposeUIViewController {
    App(
        prefs = remember {
            createDataStore()
        }
    )
}
