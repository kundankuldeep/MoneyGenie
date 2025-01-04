package com.jetbrains.moneygenie.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.components.VerticalSpace
import com.jetbrains.moneygenie.theme.Natural200
import com.jetbrains.moneygenie.theme.Natural400
import com.jetbrains.moneygenie.theme.Natural500

/**
 * Created by Kundan on 31/12/24
 **/
@Composable
fun AndroidDatePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }

    // Manage TextField value state
    var textFieldValue by remember { mutableStateOf(value) }

    // Show the date picker dialog
    if (showDatePicker) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val formattedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                textFieldValue = formattedDate
                onValueChange(formattedDate)
                showDatePicker = false
            },
            2000,
            0,
            1
        )

        // Set dismiss listener
        datePickerDialog.setOnDismissListener {
            showDatePicker = false
            // Perform any additional actions on dismiss
            println("DatePickerDialog dismissed")
        }

        // Set cancel listener
        datePickerDialog.setOnCancelListener {
            showDatePicker = false
            // Perform any additional actions on cancel
            println("DatePickerDialog canceled")
        }

        datePickerDialog.show()
    }

    Box(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                textFieldValue = formatDateInput(newValue)
                onValueChange(textFieldValue)
            },
            label = { Text(label) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar Icon"
                    )
                }
            },
            shape = CircleShape,
            modifier = modifier.fillMaxWidth(),
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
            .clickable {
                showDatePicker = true
            }
        )
    }

    VerticalSpace(10)
}

// Helper to format input manually
fun formatDateInput(input: String): String {
    val digits = input.filter { it.isDigit() }
    val builder = StringBuilder()

    digits.forEachIndexed { index, char ->
        builder.append(char)
        if (index == 1 || index == 3) builder.append("/")
    }

    return builder.toString()
}
