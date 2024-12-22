package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jetbrains.moneygenie.theme.Natural200
import com.jetbrains.moneygenie.theme.Natural400
import com.jetbrains.moneygenie.theme.Natural500

/**
 * Created by Kundan on 18/08/24
 **/

@Composable
fun FloatingLabelEditText(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = Natural200,
            focusedBorderColor = Natural200,
            focusedLabelColor = Natural500,
            unfocusedLabelColor = Natural400,
            cursorColor = Natural500
        )
    )
    VerticalSpace(10)
}