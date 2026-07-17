package com.example.rentalfinder.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.rentalfinder.R
import com.example.rentalfinder.repository.PropertyRepoImpl
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.MintGreen
import com.example.rentalfinder.view.components.InfoIconText
import com.example.rentalfinder.viewmodel.PropertyViewModel

class DetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val propertyId = intent.getStringExtra("propertyId") ?: ""

        enableEdgeToEdge()

        setContent {
            DetailBody(propertyId)
        }
    }
}

@Composable
fun DetailBody(propertyId: String) {

    val viewModel = remember {
        PropertyViewModel(PropertyRepoImpl())
    }

    val property by viewModel.properties.observeAsState()

    LaunchedEffect(propertyId) {
        viewModel.getPropertyById(propertyId)
    }

    Scaffold(
        containerColor = MintGreen
    ) { padding ->

        if (property == null) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        } else {

            val p = property!!
            val context = LocalContext.current

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                // ⭐ IMAGE SECTION
                item {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(280.dp)
                    ) {

                        AsyncImage(
                            model = p.imageUrl,
                            contentDescription = "Property Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        // Back Button
                        IconButton(
                            onClick = {
                                context.startActivity(
                                    Intent(context, DashboardActivity::class.java)
                                )
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopStart)
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                                contentDescription = null,
                                tint = ForestGreen
                            )
                        }
                    }
                }

                // ⭐ TITLE + PRICE
                item {

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                        // Title
                        Text(
                            text = p.title,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold,
                            color = ForestGreen
                        )

                        // Price
                        Text(
                            text = "Rs. ${p.price} / month",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            InfoIconText(
                                icon = R.drawable.baseline_bed_24,
                                value = p.bedrooms.toString()
                            )

                            InfoIconText(
                                icon = R.drawable.bathroom,
                                value = p.bathrooms.toString()
                            )

                            InfoIconText(
                                icon = R.drawable.refrigerator,
                                value = p.kitchens.toString()
                            )
                        }
                    }
                }

                // ⭐ DESCRIPTION
                item {

                    Text(
                        text = p.description,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 15.sp
                    )
                }

                // ⭐ PROPERTY DETAILS SECTION
                item {

                    Text(
                        text = "Property Details",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = ForestGreen,
                        modifier = Modifier.padding(16.dp)
                    )

                    val details = listOf(
                        DetailItem("City", p.city, R.drawable.baseline_location_city_24),
                        DetailItem("Area", p.area, R.drawable.baseline_map_24),
                        DetailItem("Street", p.streetAddress, R.drawable.baseline_add_road_24),
                        DetailItem("Year Built", p.yearBuilt.toString(), R.drawable.baseline_calendar_month_24),
                        DetailItem("Levels", p.levels.toString(), R.drawable.baseline_layers_24),
                        DetailItem("Tenant Type", p.tenantType, R.drawable.baseline_people_24),
                        DetailItem("Lease Type", p.leaseType, R.drawable.baseline_key_24),
                        DetailItem("Furniture", p.furnitureType, R.drawable.baseline_chair_24)
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        details.forEach { item ->

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    painter = painterResource(item.icon),
                                    contentDescription = null,
                                    tint = Gold,
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .size(22.dp)
                                )

                                Column {

                                    Text(
                                        text = item.label,
                                        fontWeight = FontWeight.SemiBold
                                    )

                                    Text(text = item.value)
                                }
                            }
                        }
                    }
                }

                // ⭐ AMENITIES
                if (p.amenities.isNotEmpty()) {

                    item {

                        Text(
                            text = "Amenities",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = ForestGreen,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    items(p.amenities) { amenity ->

                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Icon(
                                painter = painterResource(R.drawable.baseline_check_circle_24),
                                contentDescription = null,
                                tint = Gold,
                                modifier = Modifier.size(18.dp)
                            )

                            Text(
                                text = amenity,
                                modifier = Modifier.padding(start = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class DetailItem(
    val label: String,
    val value: String,
    val icon: Int
)