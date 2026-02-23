package com.example.rentalfinder.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.rentalfinder.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.ui.theme.SoftPurple

@Composable
fun PropertyCard(
    property: PropertyModel,
    onEdit: (PropertyModel) -> Unit,
    onDelete: (PropertyModel) -> Unit,
    onClick: (PropertyModel) -> Unit
){
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)   // smaller outer padding
            .clickable { onClick(property) },
        colors = CardDefaults.cardColors(
            containerColor = SoftPurple
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(4.dp)  // smaller shadow
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)  // smaller inner padding
        ) {

            AsyncImage(
                model = property.imageUrl,
                contentDescription = "Property Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),   // ⭐ reduced from 200 → 150
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.destination),
                error = painterResource(R.drawable.message)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Rs. ${property.price}",
                    fontSize = 16.sp,   // slightly smaller
                    fontWeight = FontWeight.Bold
                )

                Box {
                    IconButton(
                        onClick = { expanded = true },
                        modifier = Modifier.size(22.dp)  // smaller icon area
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_more_vert_24),
                            contentDescription = "Menu",
                            tint = Gold
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = {
                                expanded = false
                                onEdit(property)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = {
                                expanded = false
                                onDelete(property)
                            }
                        )
                    }
                }
            }

            Text(
                text = property.title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = "${property.city}, ${property.area}",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                InfoIconText(
                    icon = R.drawable.baseline_bed_24,
                    value = property.bedrooms.toString()
                )

                InfoIconText(
                    icon = R.drawable.bathroom,
                    value = property.bathrooms.toString()
                )

                InfoIconText(
                    icon = R.drawable.refrigerator,
                    value = property.kitchens.toString()
                )
            }
        }
    }
}


@Composable
fun InfoIconText(icon: Int, value: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Gold,
            modifier = Modifier.size(16.dp)  // smaller icons
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            fontSize = 12.sp
        )
    }
}