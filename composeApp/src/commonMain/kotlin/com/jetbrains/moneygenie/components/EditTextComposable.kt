package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by Kundan on 18/08/24
 **/

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    textStyle: TextStyle = TextStyle.Default.copy(color = Color.Black, fontSize = 16.sp),
    placeholderColor: Color = Color.Gray,
    backgroundColor: Color = Color.White
) {
    Card(
        shape = RoundedCornerShape(5.dp)
    ) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = placeholderColor,
                    style = textStyle
                )
            },
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

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
        label = { Text(label) }, // Floating label
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = CircleShape
    )
    VerticalSpace(10)
}