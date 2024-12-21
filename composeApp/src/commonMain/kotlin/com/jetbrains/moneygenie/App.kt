package com.jetbrains.moneygenie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jetbrains.moneygenie.data.preferences.PreferenceKeys
import com.jetbrains.moneygenie.data.preferences.PreferenceManager
import com.jetbrains.moneygenie.screens.home.HomeScreen
import com.jetbrains.moneygenie.screens.onboarding.OnBoardingScreen

@Composable
fun App(prefs: DataStore<Preferences>) {
    var isLoggedIn by remember { mutableStateOf(false) }
    var isPreferenceLoaded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        PreferenceManager.dataStore = prefs
        // Fetch the preference in a coroutine
        isLoggedIn = PreferenceManager.getPreference(PreferenceKeys.IS_LOGGED_IN, false)
        isPreferenceLoaded = true
    }

    if (isPreferenceLoaded) {
        MaterialTheme {
            BottomSheetNavigator(
                sheetContentColor = Color.White,
                sheetShape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            ) {
                val screen = if (isLoggedIn) HomeScreen else OnBoardingScreen()
                Navigator(screen) {
                    SlideTransition(it)
                }
            }
        }
    } else {
        LoadingScreen()
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}
