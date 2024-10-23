package com.jetbrains.moneygenie.composableCollection

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jetbrains.moneygenie.theme.Color_P900

/**
 * Created by Kundan on 03/10/24
 **/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppBar(content: @Composable () -> Unit) {
    TopAppBar(
        title = content, colors =
        TopAppBarColors(
            containerColor = Color_P900,
            actionIconContentColor = Color_P900,
            navigationIconContentColor = Color_P900,
            scrolledContainerColor = Color_P900,
            titleContentColor = Color.White
        )
    )
}
