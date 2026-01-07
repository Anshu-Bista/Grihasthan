package com.example.rentalfinder.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.OffWhite

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AmenitiesChips(
    label: String,
    amenities: List<String>,
    selectedAmenities: Set<String>,
    onSelectionChange: (Set<String>) -> Unit
) {
    // LABEL
    Text(
        text = label,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        amenities.forEach { amenity ->
            val isSelected = selectedAmenities.contains(amenity)
            FilterChip(
                selected = isSelected,
                onClick = {
                    onSelectionChange(
                        if (isSelected)
                            selectedAmenities - amenity
                        else
                            selectedAmenities + amenity
                    )
                },
                label = {
                    Text(
                        text = amenity,
                        color = if (isSelected) OffWhite else Gold
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Gold,
                    containerColor = Color.Transparent
                )
            )

        }
    }
}
