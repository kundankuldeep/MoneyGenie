package com.jetbrains.moneygenie.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.expects.FontTypeFace
import com.jetbrains.moneygenie.expects.provideFont

/**
 * Created by Kundan on 18/08/24
 **/
@Composable
fun SelectableChip(
    text: String,
    isSelected: Boolean,
    onSelectionChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isSelected) Color(0xFF246432) else Color.White // Green when selected, white otherwise
    val contentColor =
        if (isSelected) Color.White else Color.Black // White text when selected, black otherwise
    val borderColor =
        if (isSelected) Color.Transparent else Color(0xFFDEDEDE) // No border when selected, gray otherwise

    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .clickable(
                onClick = onSelectionChanged,
                indication = null, // Disable ripple effect
                interactionSource = remember { MutableInteractionSource() } // Required when indication is null
            ),
        color = backgroundColor,
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = text,
            color = contentColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.body2,
            fontFamily = provideFont(FontTypeFace.SEMI_BOLD)
        )
    }
}