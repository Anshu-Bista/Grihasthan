package com.example.rentalfinder.view.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun PasswordField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    FormField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        keyboardType = KeyboardType.Password,
        regex = Regex("^(?=.*[A-Za-z])(?=.*\\d).{8,}$"),
        errorMessage = "Min 8 chars, 1 letter & 1 number"
    )
}
