package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.jetbrains.moneygenie.theme.Natural200
import com.jetbrains.moneygenie.theme.Natural400
import com.jetbrains.moneygenie.theme.Natural500
import moneygenie.composeapp.generated.resources.Res
import moneygenie.composeapp.generated.resources.ic_eye
import moneygenie.composeapp.generated.resources.ic_eye_off
import org.jetbrains.compose.resources.painterResource

/**
 * Created by Kundan on 18/08/24
 **/

@Composable
fun FloatingLabelEditText(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isPasswordField: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (isPasswordField && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.fillMaxWidth(),
        shape = CircleShape,
        trailingIcon = {
            if (isPasswordField) {
                val image = if (passwordVisible) Res.drawable.ic_eye else Res.drawable.ic_eye_off
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(painter = painterResource(image), contentDescription = description)
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
    VerticalSpace(10)
}