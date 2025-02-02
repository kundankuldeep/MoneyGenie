package com.jetbrains.moneygenie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.Natural200
import com.jetbrains.moneygenie.theme.Natural400
import com.jetbrains.moneygenie.theme.Natural500

/**
 * Created by Kundan on 04/01/25
 **/
@Composable
fun DropdownComposable(
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Select an option",
    expandedInitially: Boolean = false,
    dropdownIcon: @Composable (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(expandedInitially) }
    var currentSelection by remember { mutableStateOf(selectedOption) }

    Box(modifier = modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = currentSelection ?: "",
                onValueChange = {},
                label = { Text(label) },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = CircleShape,
                trailingIcon = {
                    if (dropdownIcon != null) {
                        dropdownIcon()
                    } else {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(
                                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = "Toggle dropdown"
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Natural200,
                    focusedBorderColor = Natural200,
                    focusedLabelColor = Natural500,
                    unfocusedLabelColor = Natural400,
                    cursorColor = Natural500
                )
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ) {
                    expanded = !expanded
                }
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.9f).background(Color.White)
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        currentSelection = option
                        expanded = false
                        onOptionSelected(option)
                    }
                )
            }
        }
    }
}