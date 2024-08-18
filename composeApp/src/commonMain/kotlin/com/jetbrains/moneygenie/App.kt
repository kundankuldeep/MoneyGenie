package com.jetbrains.moneygenie

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.moneygenie.screens.onboarding.OnBoardingScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(OnBoardingScreen)
    }
}
