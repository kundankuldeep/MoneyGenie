package com.jetbrains.moneygenie.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.MGTypography
import com.jetbrains.moneygenie.theme.Primary700
import com.jetbrains.moneygenie.theme.White

/**
 * Created by Kundan on 18/08/24
 **/
@Composable
fun GradientIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector
) {
    // Define the gradient colors
    val gradientColors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32))

    // Draw the circular button with a gradient background
    Box(
        modifier = modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(brush = Brush.radialGradient(gradientColors)),
        contentAlignment = Alignment.Center // Center the content inside the Box
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = icon,
            contentDescription = "Next",
            tint = Color.White
        )
    }
}

@Composable
fun MGButton(
    modifier: Modifier = Modifier,
    isFullWidth: Boolean = false,
    text: String,
    type: MGButtonType = MGButtonType.SOLID,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .then(if (isFullWidth) Modifier.fillMaxWidth() else Modifier.wrapContentWidth()) // Apply conditional modifier
            .height(50.dp), // Set height as per your design
        colors = when (type) {
            MGButtonType.SOLID -> ButtonDefaults.buttonColors(
                containerColor = Primary700, // Solid button color (green)
                contentColor = White
            )

            MGButtonType.OUTLINE -> ButtonDefaults.outlinedButtonColors(
                contentColor = Primary700 // Green color for text
            )

            MGButtonType.TEXT -> ButtonDefaults.textButtonColors(
                contentColor = Primary700 // Green color for text
            )
        },
        shape = CircleShape, // Rounded corners as per the design
        border = when (type) {
            MGButtonType.OUTLINE -> BorderStroke(1.dp, Primary700)
            else -> null
        },
        elevation = if (type == MGButtonType.SOLID) ButtonDefaults.buttonElevation(4.dp) else ButtonDefaults.buttonElevation(
            0.dp
        )
    ) {
        Text(text = text, style = MGTypography().bodySemiBold)
    }
}


enum class MGButtonType {
    SOLID,
    OUTLINE,
    TEXT
}