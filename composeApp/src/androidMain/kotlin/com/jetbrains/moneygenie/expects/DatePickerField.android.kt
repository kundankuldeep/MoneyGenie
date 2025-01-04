package com.jetbrains.moneygenie.expects

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jetbrains.moneygenie.ui.AndroidDatePickerField

/**
 * Created by Kundan on 31/12/24
 **/
@Composable
actual fun DatePickerField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier
) {
    AndroidDatePickerField(label, value, onValueChange, modifier)
}