package com.example.rentalfinder.view.components


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType


@Composable
fun EmailField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    FormField(
        label = label,
        value = value,
        onValueChange = onValueChange,
        placeholder = "example@email.com",
        keyboardType = KeyboardType.Email,
        regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),
        errorMessage = "Invalid email format"
    )
}
