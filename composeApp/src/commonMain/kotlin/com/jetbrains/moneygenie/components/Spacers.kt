package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Kundan on 27/10/24
 **/

@Composable
fun VerticalSpace(height: Int) {
    Spacer(modifier = Modifier.height(height.dp))
}

@Composable
fun HorizontalSpace(width: Int) {
    Spacer(modifier = Modifier.height(width.dp))
}