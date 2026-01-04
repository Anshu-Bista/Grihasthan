package com.example.rentalfinder.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxDigits: Int = 2
) {
    FormField(
        label = label,
        value = value,
        onValueChange = {
            if (it.all { char -> char.isDigit() }) {
                onValueChange(it)
            }
        },
        keyboardType = KeyboardType.Number,
        regex = Regex("^\\d{1,$maxDigits}$"),
        errorMessage = "Only $maxDigits digits allowed",
        maxLength = maxDigits,
        modifier = modifier
    )
}
