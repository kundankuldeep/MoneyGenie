package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Created by Kundan on 20/12/24
 **/
@Composable
fun GenderSelectionChipGroup(
    modifier: Modifier = Modifier
) {
    var selectedGender by remember { mutableStateOf<String?>(null) }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectableChip(
            text = "Male",
            isSelected = selectedGender == "Male",
            onSelectionChanged = {
                selectedGender = if (selectedGender == "Male") null else "Male"
            }
        )

        SelectableChip(
            text = "Female",
            isSelected = selectedGender == "Female",
            onSelectionChanged = {
                selectedGender = if (selectedGender == "Female") null else "Female"
            }
        )
    }
}