package com.example.rentalfinder.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.rentalfinder.model.PropertyModel
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.viewmodel.PropertyViewModel

@Composable
fun PropertyCard(property: PropertyModel){
    Card(modifier = Modifier.fillMaxWidth()
        .padding(15.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
        ) {
            AsyncImage(
                model = property.imageUrl,
                contentDescription = "Property Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.destination),
                error = painterResource(R.drawable.message)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = property.price.toString(),
                    style = MaterialTheme.typography.headlineMedium
                )
                Image(painter = painterResource(R.drawable.baseline_favorite_24),
                    contentDescription = null)
            }

            Text(
                text = property.title,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Rs. ${property.price}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "${property.city}, ${property.location}",
                style = MaterialTheme.typography.bodySmall
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                // 1️⃣ Bedrooms
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_bed_24),
                        contentDescription = "Bedrooms",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${property.bedrooms}")
                }

                // 2️⃣ Bathrooms
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.bathroom),
                        contentDescription = "Bathrooms",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${property.bathrooms}")
                }

                // 3️⃣ Kitchens
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.refrigerator),
                        contentDescription = "Kitchens",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("${property.kitchens}")
                }
            }


        }
    }
}