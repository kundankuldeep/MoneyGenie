package com.jetbrains.moneygenie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jetbrains.moneygenie.theme.MGTypography

/**
 * Created by Kundan on 20/12/24
 **/

enum class Genders(var value: String) {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other")
}

fun getGenderFromValue(value: String?): Genders? {
    if (value == "Male") {
        return Genders.MALE
    }
    if (value == "Female") {
        return Genders.FEMALE
    }
    if (value == "Other") {
        return Genders.OTHER
    }

    return null
}

@Composable
fun GenderSelectionChipGroup(
    isFillMaxWidth: Boolean = false,
    paddingValues: PaddingValues? = null,
    selected: Genders? = null,
    onSelectionChanged: (Genders) -> Unit
) {
    var selectedGender by mutableStateOf(selected)
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Select Gender", style = MGTypography().bodyRegularS)
        VerticalSpace(10)
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
        VerticalSpace(10)
    }
}

enum class TransactionType(val value: String) {
    Lent("Lent"),
    Borrowed("Borrowed")
}

@Composable
fun OweTypeChipGroup(
    isFillMaxWidth: Boolean = false,
    onSelectionChanged: (TransactionType) -> Unit
) {
    var transactionType by remember { mutableStateOf<TransactionType?>(null) }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SelectableChip(
            text = TransactionType.Lent.value,
            isSelected = transactionType == TransactionType.Lent,
            onSelectionChanged = {
                transactionType =
                    if (transactionType == TransactionType.Lent) null else TransactionType.Lent
                onSelectionChanged.invoke(TransactionType.Lent)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
        )

        SelectableChip(
            text = TransactionType.Borrowed.value,
            isSelected = transactionType == TransactionType.Borrowed,
            onSelectionChanged = {
                transactionType =
                    if (transactionType == TransactionType.Borrowed) null else TransactionType.Borrowed
                onSelectionChanged.invoke(TransactionType.Borrowed)
            },
            modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier, // Equal width for each chip
        )
    }
}

enum class TransactionFilterType(val value: String) {
    Older("Older"),
    Lent("Lent"),
    Borrowed("Borrowed")
}

@Composable
fun TransactionFilters(
    selectedFilter: TransactionFilterType?,
    isFillMaxWidth: Boolean = false,
    onSelectionChanged: (TransactionFilterType?) -> Unit
) {
    var currentFilter by remember { mutableStateOf(selectedFilter) }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TransactionFilterType.entries.forEach { filter ->
            SelectableChip(
                text = filter.value,
                isSelected = currentFilter == filter,
                onSelectionChanged = {
                    currentFilter = if (currentFilter == filter) null else filter
                    onSelectionChanged(currentFilter)
                },
                modifier = if (isFillMaxWidth) Modifier.weight(1f) else Modifier
            )
        }
    }
}
