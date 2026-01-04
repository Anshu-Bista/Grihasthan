package com.example.rentalfinder.view.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.ui.theme.SandBiege


@Composable
fun DescriptionField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int = 500
) {
    Column {

        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = ForestGreen
        )

        OutlinedTextField(
            value = value,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp),
            maxLines = 6,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = OffWhite,
                focusedContainerColor = OffWhite,
                focusedIndicatorColor = SandBiege,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = "${value.length} / $maxLength",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}
