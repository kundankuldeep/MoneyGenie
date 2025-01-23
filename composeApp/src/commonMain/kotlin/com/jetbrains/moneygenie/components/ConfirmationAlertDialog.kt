package com.jetbrains.moneygenie.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.jetbrains.moneygenie.theme.Primary700

/**
 * Created by Kundan on 20/01/25
 **/

data class ConfirmationDialogData(
    var title: String,
    var subTitle: String,
    var positiveButton: String = "Confirm",
    var negativeButton: String = "Cancel",
)

@Composable
fun ShowConfirmationDialog(
    data: ConfirmationDialogData,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(data.title) },
        text = { Text(data.subTitle) },
        confirmButton = {
            TextButton(onClick = {
                onConfirm()
                onDismiss()
            }) {
                Text(data.positiveButton, color = Primary700)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(data.negativeButton, color = Primary700)
            }
        }
    )
}