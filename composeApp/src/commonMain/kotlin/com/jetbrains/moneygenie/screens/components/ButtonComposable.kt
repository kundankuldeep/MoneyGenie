package com.jetbrains.moneygenie.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Created by Kundan on 18/08/24
 **/
@Composable
fun GradientCircleButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Define the gradient colors
    val gradientColors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))

    // Draw the circular button with a gradient background
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(brush = Brush.radialGradient(gradientColors))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center // Center the content inside the Box
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, // Use the right arrow icon
            contentDescription = "Next",
            tint = Color.White
        )
    }
}