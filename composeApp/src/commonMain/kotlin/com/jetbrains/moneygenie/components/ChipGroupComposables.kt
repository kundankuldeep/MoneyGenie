package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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

enum class Genders(val value: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other")
}

@Composable
fun GenderSelectionChipGroup(
    isFillMaxWidth: Boolean = false,
    paddingValues: PaddingValues? = null,
    onSelectionChanged: (Genders) -> Unit
) {
    var selectedGender by remember { mutableStateOf<Genders?>(null) }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectableChip(
            text = Genders.MALE.value,
            isSelected = selectedGender == Genders.MALE,
            onSelectionChanged = {
                selectedGender = if (selectedGender == Genders.MALE) null else Genders.MALE
                onSelectionChanged.invoke(Genders.MALE)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
            paddingValues = paddingValues
        )

        SelectableChip(
            text = Genders.FEMALE.value,
            isSelected = selectedGender == Genders.FEMALE,
            onSelectionChanged = {
                selectedGender = if (selectedGender == Genders.FEMALE) null else Genders.FEMALE
                onSelectionChanged.invoke(Genders.FEMALE)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
            paddingValues = paddingValues
        )

        SelectableChip(
            text = Genders.OTHER.value,
            isSelected = selectedGender == Genders.OTHER,
            onSelectionChanged = {
                selectedGender = if (selectedGender == Genders.OTHER) null else Genders.OTHER
                onSelectionChanged.invoke(Genders.OTHER)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
            paddingValues = paddingValues
        )
    }
}

enum class OweType(val value: String) {
    YouOwe("You Owe"),
    TheyOwe("They Owe")
}

@Composable
fun OweTypeChipGroup(
    isFillMaxWidth: Boolean = false,
    onSelectionChanged: (OweType) -> Unit
) {
    var oweType by remember { mutableStateOf<OweType?>(null) }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectableChip(
            text = OweType.YouOwe.value,
            isSelected = oweType == OweType.YouOwe,
            onSelectionChanged = {
                oweType = if (oweType == OweType.YouOwe) null else OweType.YouOwe
                onSelectionChanged.invoke(OweType.YouOwe)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
        )

        SelectableChip(
            text = OweType.TheyOwe.value,
            isSelected = oweType == OweType.TheyOwe,
            onSelectionChanged = {
                oweType = if (oweType == OweType.TheyOwe) null else OweType.TheyOwe
                onSelectionChanged.invoke(OweType.TheyOwe)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
        )
    }
}