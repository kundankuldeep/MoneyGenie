package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.Natural200
import com.jetbrains.moneygenie.theme.Natural400
import com.jetbrains.moneygenie.theme.Natural500

/**
 * Created by Kundan on 05/01/25
 **/
@Composable
fun RoundedCornerSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search",
    modifier: Modifier = Modifier,
    onSearch: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        singleLine = true,
        shape = CircleShape,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        trailingIcon = {
            IconButton(onClick = { onSearch?.invoke() }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
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
}