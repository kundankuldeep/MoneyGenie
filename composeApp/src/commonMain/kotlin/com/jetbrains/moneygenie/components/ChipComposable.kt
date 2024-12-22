package com.jetbrains.moneygenie.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.MGTypography

/**
 * Created by Kundan on 18/08/24
 **/
@Composable
fun SelectableChip(
    text: String,
    isSelected: Boolean,
    onSelectionChanged: () -> Unit,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues? = null
) {
    val backgroundColor =
        if (isSelected) Color(0xFF246432) else Color.White // Green when selected, white otherwise
    val contentColor =
        if (isSelected) Color.White else Color.Black // White text when selected, black otherwise
    val borderColor =
        if (isSelected) Color.Transparent else Color(0xFFDEDEDE) // No border when selected, gray otherwise

    val paddings = paddingValues ?: PaddingValues(horizontal = 16.dp, vertical = 12.dp)

    Surface(
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(100)
            )
            .clickable(
                onClick = onSelectionChanged,
                indication = null, // Disable ripple effect
                interactionSource = remember { MutableInteractionSource() } // Required when indication is null
            ),
        color = backgroundColor,
        shape = RoundedCornerShape(100),
    ) {
        Text(
            text = text,
            color = contentColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(paddings),
            style = MGTypography().bodyRegular
        )
    }
}