package com.jetbrains.moneygenie

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jetbrains.moneygenie.screens.onboarding.OnBoardingScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(OnBoardingScreen()) {
            SlideTransition(it)
        }
    }
}
